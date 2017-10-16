/**
 *    Copyright 2006-2017 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.generator.codegen.mybatis3.model.helpers;

import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.internal.util.JavaBeansUtil;

/**
 * Created by Administrator on 2017/10/13 0013.
 */
public class ExampleGeneratorHelper {

    public static void generatePageInfoStr(Field field, TopLevelClass topLevelClass) {
        addWithSingleSize(topLevelClass);
        addWithDoublePara(topLevelClass);
        addGetMethod(field,topLevelClass);
    }

    private static void addGetMethod(Field field ,TopLevelClass topLevelClass) {
        generateGetMethod(field,topLevelClass);
    }

    private static void addWithDoublePara(TopLevelClass topLevelClass) {
        Method doubleParaMethod = new Method();
        doubleParaMethod.setName("createPageConditions");
        doubleParaMethod.setVisibility(JavaVisibility.PUBLIC);
        doubleParaMethod.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(),"currentPage"));
        doubleParaMethod.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(),"pageSize"));

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("this.").append("pageConditionsStr = ");
        stringBuilder.append("\"limit \"").append(" + ")
                 .append("currentPage").append(" + ")
                 .append("\",\"").append(" + ").append("pageSize").append(";");
        doubleParaMethod.addBodyLine(stringBuilder.toString());
        topLevelClass.addMethod(doubleParaMethod);
    }

    private static void addWithSingleSize(TopLevelClass topLevelClass) {
        Method singleParaMethod = new Method();
        singleParaMethod.setVisibility(JavaVisibility.PUBLIC);
        singleParaMethod.setName("createPageConditions");
        singleParaMethod.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(),"pageSize"));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("this.").append("pageConditionsStr = ");
        stringBuilder.append("\"limit \"").append(" + ").append("pageSize").append(";");
        singleParaMethod.addBodyLine(stringBuilder.toString());
        topLevelClass.addMethod(singleParaMethod);
    }

    public static void generateGetMethod(Field field, InnerClass topLevelClass) {
        Method getMethod = new Method();
        getMethod.setVisibility(JavaVisibility.PUBLIC);
        getMethod.setReturnType(field.getType());
        getMethod.setName(JavaBeansUtil.getGetterMethodName(field.getName(),field.getType())); //$NON-NLS-1$
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("return").append(" this.").append(field.getName()).append(";");
        getMethod.addBodyLine(stringBuilder.toString());
        topLevelClass.addMethod(getMethod);
    }

    public static void generateSetMethod(Field field, InnerClass topLevelClass) {
        Method setMethod = new Method();
        setMethod.setVisibility(JavaVisibility.PUBLIC);
        setMethod.setName(JavaBeansUtil.getSetterMethodName(field.getName())); //$NON-NLS-1$
        setMethod.addParameter(new Parameter(field.getType(), field.getName())); //$NON-NLS-1$
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("this.").append(field.getName()).append(" = ").append(field.getName()).append(";");
        setMethod.addBodyLine(stringBuilder.toString());
        topLevelClass.addMethod(setMethod);

    }
}
