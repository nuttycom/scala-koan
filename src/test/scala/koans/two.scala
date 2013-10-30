package koans
package two

import org.scalacheck._

object IdSpec extends Properties("Id") {
  import Prop.forAll
  import Gen._
  import Arbitrary.arbitrary

  implicit def ieq = (i1: Int, i2: Int) => i1 == i2

  property("functor identity") = forAll { (fa: Id[Int]) =>
    FunctorLaw.identity(fa)
  }

  property("functor composition") = forAll { (fa: Id[Int]) =>
    FunctorLaw.composition(fa, (_: Int) + 1, (_: Int) + 1)
  }
}

