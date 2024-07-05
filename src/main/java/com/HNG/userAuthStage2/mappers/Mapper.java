package com.HNG.userAuthStage2.mappers;

public interface Mapper <A,B>{

    B mapTo(A a);

    A mapFrom(B b);
}
