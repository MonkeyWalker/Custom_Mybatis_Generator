package com.runlion;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import sun.awt.OSInfo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/13 0013.
 */
@Mojo(name = "generate")
public class RunlionGeneratorMOJO extends AbstractMojo {
    @Parameter(defaultValue = "3307")
    private String port;


    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    @Parameter(required = true)
    private String[] tables;


    @Parameter(required = true, defaultValue = "com.opengroup.hongshi.")
    private String packageLocation;
    @Parameter(required = true,defaultValue = "jdbc:mysql://db.hongshiwl.com:")
    private String dbUrl;
    @Parameter
    private String targetMapperPackage;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        String parentProName = project.getParent().getArtifactId();
        File projectRootDir = project.getBasedir();
        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        InputStream configStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = null;
        try {
            config = cp.parseConfiguration(configStream);
            config.getContexts().get(0).getCommentGeneratorConfiguration().addProperty("suppressAllComments", "true");
            Context context = config.getContexts().get(0);

            context.getJavaModelGeneratorConfiguration().setTargetPackage(packageLocation + parentProName+".dal.generate");
            if (OSInfo.getOSType() == OSInfo.OSType.WINDOWS){
                context.getJavaModelGeneratorConfiguration().setTargetProject(projectRootDir + "\\src\\main\\java");
            }else {
                context.getJavaModelGeneratorConfiguration().setTargetProject(projectRootDir + "/src/main/java");
            }


            context.getJavaClientGeneratorConfiguration().setTargetPackage(packageLocation + parentProName+".dal.generate");
            if (OSInfo.getOSType() == OSInfo.OSType.WINDOWS){
                context.getJavaClientGeneratorConfiguration().setTargetProject(projectRootDir + "\\src\\main\\java");
            }else {
                context.getJavaClientGeneratorConfiguration().setTargetProject(projectRootDir + "/src/main/java");
            }


            if (targetMapperPackage != null){
                context.getSqlMapGeneratorConfiguration().setTargetPackage(targetMapperPackage);
            }else {
                context.getSqlMapGeneratorConfiguration().setTargetPackage(parentProName + "_"+ "dal.sql.common");
            }
            if (OSInfo.getOSType() == OSInfo.OSType.WINDOWS){
                context.getSqlMapGeneratorConfiguration().setTargetProject(projectRootDir + "\\src\\main\\resources");
            }else{
                context.getSqlMapGeneratorConfiguration().setTargetProject(projectRootDir + "/src/main/resources");
            }


            StringBuilder sb = new StringBuilder();
            sb.append(dbUrl).append(port).append("/").append(parentProName);
            context.getJdbcConnectionConfiguration().setConnectionURL(sb.toString());

            List<TableConfiguration> tableConfigurationList = context.getTableConfigurations();
            tableConfigurationList.clear();
            for (String table : tables){
                TableConfiguration tableConfiguration = new TableConfiguration(context);
                tableConfiguration.setTableName(table);
                tableConfigurationList.add(tableConfiguration);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLParserException e) {
            e.printStackTrace();
        }
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = null;
        try {
            myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }

        try {
            myBatisGenerator.generate(new ProgressCallback() {
                @Override
                public void introspectionStarted(int totalTasks) {

                }

                @Override
                public void generationStarted(int totalTasks) {

                }

                @Override
                public void saveStarted(int totalTasks) {

                }

                @Override
                public void startTask(String taskName) {

                }

                @Override
                public void done() {

                }

                @Override
                public void checkCancel() throws InterruptedException {

                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
