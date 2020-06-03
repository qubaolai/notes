### 一、定义person类型

```java
package com.almond.demo;

public class Person {

    private String name;

    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
	//无参构造器
    public Person() {
        System.out.println("无参构造器");
    }
	//全参构造器
    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
        System.out.println("两个参数构造器: " + name + ", " + age);
    }
	//一个参数的私有构造器
    private Person(String name) {
        this.name = name;
        System.out.println("一个参数私有构造器: " + name);
    }
}

```

### 二、创建带有主方法的测试类,获取Person类的class对象

```java
package com.almond.demo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Test {

    public static void main(String[] args) throws ClassNotFoundException{
        //通过构造器获取Person对象
        Person p1 = new Person();

        //通过Object类的getClass()方法获取class对象
        Class<? extends Person> p1Class = p1.getClass();
        System.out.println(p1Class);

        //通过类的静态属性获取Person类的class对象
        Class<Person> p2Class = Person.class;
        System.out.println(p2Class);

        //通过Class类的forName()方法获取class对象
        Class<?> p3Class = Class.forName("com.almond.demo.Person");
        System.out.println(p3Class);

        System.out.println("p1Class == p2Class: " + (p1Class == p2Class));

        System.out.println("p2Class == p3Class: " + (p2Class == p3Class));
    }

}
//输出结果
无参构造器
Object类的getClass方法获取: class com.almond.demo.Person
Person类的静态属性获取: class com.almond.demo.Person
Class类的forName()获取: class com.almond.demo.Person
p1Class == p2Class: true
p2Class == p3Class: true
```

### 三、获取构造器

1. ###### 获取公有构造器

```java
package com.almond.demo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ConstructorTest {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //通过类的静态属性获取class对象
        Class<Person> personClass = Person.class;
        //获取构造器,getConstructor()方法可选参数,有参数时需要与声明的公有构造器参数类型一致
        Constructor<Person> constructor = personClass.getConstructor();
        Person person = constructor.newInstance();
        Constructor<?>[] constructors = personClass.getConstructors();
        for(Constructor con : constructors){
            System.out.println(con);
        }
    }
}

```

[![tdq5ng.png](https://s1.ax1x.com/2020/06/03/tdq5ng.png)](https://imgchr.com/i/tdq5ng)

###### <font color="orange">通过调用类的class对象的getConstructors()方法只能获取公有构造器</font>

###### 获取有参构造器,代码如下

```java
Constructor<Person> constructor = personClass.getConstructor(String.class, Integer.class);
```

###### 注意getConstructor()方法的参数类型

###### Person类中的有参构造器参数类型为String和Integer,构造器如下:

```java
public Person(String name, Integer age) {
    this.name = name;
    this.age = age;
    System.out.println("两个参数构造器: " + name + ", " + age);
}
```

###### 下图中使用new关键字实例化时,age是int声明的,在调用过程中可以通过封箱转为Integer,但是在使用getConstructor()方法获取构造器时只能使用声明构造器时的参数类型

###### 类型必须一致!!!

###### 使用错误类型报如下异常:

# 图片占位: 构造器参数类型错误

###### 获取私有构造器可以使用class对象的getDeclaredConstructor(Class<?>... parameterTypes)方法

```java
//获取私有构造器
Constructor<Person> privateCon = personClass.getDeclaredConstructor(String.class);
//设置私有构造器的可见性
privateCon.setAccessible(true);
//使用私有构造器实例化
Person zhangSan = privateCon.newInstance("zhangSan");
//这时候则是使用私有构造器实例化
```

###### 注意一定要设置私有构造器的可见性,不设置或者设置为false则会报错,报错信息如下:

# 图片占位: 私有构造器不设置可见性

###### 设置可见性后执行结果如下:

[![tdxM11.png](https://s1.ax1x.com/2020/06/03/tdxM11.png)](https://imgchr.com/i/tdxM11)

