package cn.bestsort.bbslite.service;

/**
 * 这里定义了一个空接口是因为如果Server 未实现接口时是通过CGLIB进行代理,KeyGenerator会生成CGLIB$$xxxxx的内容
 * 而通过接口实现是通过jdk代理
 */
public interface BBSService {}
