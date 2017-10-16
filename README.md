# Custom_Mybatis_Generator
根据[mybatis generator](https://github.com/mybatis/generator/tree/master/core)作出的一些修改，添加了一些新的特性

##　关于第一个工程

generator 是对于mybatis genertor作出的修改，在生成的example中增加了`group by`语句，并在生成的 example类中增加了一个持有所有JAVA name 对应数据库字段的
常量的内部类，用于拼接 group by和order by条件时使用。这些修改已经提交到原Mybatis Generator

## 对于第二个工程

runlion generator 是我针对所在项目组**[没有敏感资源]**做的一个maven 插件，用于封装mybatis generator的配置。
这样在项目组其他小伙伴使用时，只需要根据作出一点小的变动即可。


最后的java类用来作为拼接order by 和 group by语句时使用
