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
package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * Created by Administrator on 2017/10/12 0012.
 */
public class PageInfoElementGenerator extends
        AbstractXmlElementGenerator {

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("sql");
        answer.addAttribute(new Attribute("id",introspectedTable.getPageInfoID()));

        answer.addElement(new TextElement("limit"));

        StringBuilder sb = new StringBuilder();
        sb.append(introspectedTable.getPageInfoCurrentPageAlias());
        // 不等 -1 说明分页是有值的
        sb.append(" != -1");
        XmlElement currentPageElement = new XmlElement("if");
        currentPageElement.addAttribute(new Attribute("test",sb.toString()));
        answer.addElement(currentPageElement);
        sb.setLength(0);
        sb.append("   #{");
        sb.append(introspectedTable.getPageInfoCurrentPageAlias());
        sb.append("},");
        answer.addElement(new TextElement(sb.toString()));

        sb.setLength(0);
        sb.append(introspectedTable.getPageInfoSizeAlias());
        // 不等 -1 说明是分页是有值的
        sb.append(" != -1");
        XmlElement sizeElement = new XmlElement("if");
        sizeElement.addAttribute(new Attribute("test",sb.toString()));
        answer.addElement(sizeElement);
        sb.setLength(0);
        sb.append("   #{");
        sb.append(introspectedTable.getPageInfoSizeAlias());
        sb.append("}");
        answer.addElement(new TextElement(sb.toString()));

        parentElement.addElement(answer);
    }
}
