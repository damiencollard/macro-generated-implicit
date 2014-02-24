# Scala macro-generated implicit in companion object.

This project defines an annotation macro, ``MakeMeta``, which
generates an implicit object extending ``Meta[C]`` within the
companion object of class ``C``.

The object is properly generated as it can be accessed explicitely by
its name, ``meta``, but the compiler does not consider it a candidate
for ``implicitly[Meta[C]]`` as I expected it would:

```
[error] .../implicit-generated-by-macro-in-companion/core/src/main/scala/org/experiment/Main.scala:13:
                could not find implicit value for parameter e: org.experiment.Meta[org.experiment.Dummy]
[error]     implicitly[Meta[Dummy]].info
[error]               ^
[error] one error found
```

Note that, while it looks like an implicit macro could do what I want,
i.e. have an implicit instance of a type class generated for a given
case class, I'd rather use an annotation macro for three reasons:

 1. The classes will be annotated for reasons other than the code
    generation needs presented here.

 2. The annotation macro offers control as to when/where the implicit
    instance is created, which the implicit macro does not: compilation
    of the annotated instance *vs* any location where the implicit is
    looked up.

 3. The annotation macro in the actual project is more complex and
    performs much more involved operations than the toy version
    presented here.
