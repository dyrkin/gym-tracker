package com.dyrkin.tracker.core.vo

/**
  * @author eugene zadyra
  */
class VoMapperSpec {
  val data = Vector(
    ((1,"PROGRAM1",1,1),Some((1,"WORKOUT1")),Some((1,"EXERCISE1",1))),
    ((1,"PROGRAM1",1,1),Some((1,"WORKOUT1")),Some((2,"EXERCISE2",1))),
    ((1,"PROGRAM1",1,1),Some((1,"WORKOUT1")),Some((3,"EXERCISE3",1))),
    ((1,"PROGRAM1",1,1),Some((2,"WORKOUT2")),Some((4,"EXERCISE4",1))),
    ((1,"PROGRAM1",1,1),Some((2,"WORKOUT2")),Some((5,"EXERCISE5",1))),
    ((1,"PROGRAM1",1,1),Some((3,"WORKOUT3")),None))
}
