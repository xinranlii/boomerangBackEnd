package com.groupproject.boomerang.model;

/**
 * 1. 一个abstract class ie. {Responsebody 真正的payload 有用的东西  +  statusCode}
      所有responsebody 的妈妈
 */
// Generics
public abstract class Response<E> {

    public int statusCode;
    public E responsebody;

}
