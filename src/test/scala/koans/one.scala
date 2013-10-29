package koans.one

import org.scalacheck._
import Maybe._

object MaybeSpec extends Properties("Maybe") {
  import Prop.forAll
  import Gen._
  import Arbitrary.arbitrary
  
  implicit def ieq = (i1: Int, i2: Int) => i1 == i2

  implicit def arbMaybe[T: Arbitrary]: Arbitrary[Maybe[T]] = Arbitrary {
    for {
      t <- arbitrary[T]
      m <- oneOf(some(t), none[T])
    } yield m 
  }

  property("map identity") = forAll { (m: Maybe[Int]) =>
    m.map(a => a).equalTo(m)
  }

  property("map composition") = forAll { (m: Maybe[Int]) =>
    val f = (_: Int) + 1
    m.map(f).map(f).equalTo(m.map(f andThen f))
  }
  
  property("flatMap associativity") = forAll { (m: Maybe[Int]) =>
    val assocL = m.flatMap(i0 => some(i0 + 1)).flatMap(i1 => some(i1 + 1))
    val assocR = m.flatMap(i0 => some(i0 + 1).flatMap(i1 => some(i1 + 1)))

    assocL equalTo assocR
  }

  property("getOrElse on none") = { 
    none[Int].getOrElse(1) == 1
  }

  property("getOrElse on some") = forAll { (i: Int, j: Int) =>
    some(i).getOrElse(j) == i
  }

  property("filter on some") = forAll { (i: Int) =>
    some(i).filter(_ > 0).equalTo(if (i > 0) some(i) else none[Int])
  }

  property("filter on none") = { 
    none[Int].filter(_ > 0).equalTo(none[Int])
  }

  property("foreach on some") = forAll { (i: Int) =>
    var found = ""
    some(i).foreach(i0 => found = i0.toString)
    found == i.toString
  }

  property("foreach on none") = { 
    var found = "abcde"
    none[Int].foreach(i0 => found = i0.toString)
    found == "abcde"
  }

  property("isDefined on some") = some(0).isDefined
  property("isDefined on none") = !none[Int].isDefined

  property("isEmpty on some") = !some(0).isEmpty
  property("isEmpty on none") = none[Int].isEmpty

  property("isEmpty complements isDefined") = forAll { (m: Maybe[Int]) =>
    m.isEmpty == !m.isDefined
  }

  property("get on some") = some(0).get == 0
  property("get on none") = try { none[Int].get; false } catch { case ex: Exception => true }

  property("some orElse some") = forAll { (i1: Int, i2: Int) => 
    some(i1).orElse(some(i2)).equalTo(some(i1)) 
  }

  property("some orElse none") = forAll { (i: Int) => 
    some(i).orElse(none[Int]).equalTo(some(i))
  }

  property("none orElse some") = forAll { (i: Int) =>
    none[Int].orElse(some(i)).equalTo(some(i))
  }

  property("none orElse none") = none[Int].orElse(none[Int]).equalTo(none[Int])

  property("some toLeft") = some(0).toLeft(1) == Left(0)
  property("none toLeft") = none[Int].toLeft(1) == Right(1)
  property("some toRight") = some(0).toRight(1) == Right(0)
  property("none toRight") = none[Int].toRight(1) == Left(1)

  property("some toList") = some(0).toList == List(0)
  property("none toList") = none[Int].toList == Nil
}

