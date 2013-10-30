package koans

import org.scalacheck._

object FunctorLaw {
  def identity[F[_], A](fa: F[A])(implicit F: Functor[F], eql: (F[A], F[A]) => Boolean): Prop = {
    eql(fa, F.fmap(Predef.identity[A]) { fa })
  }

  def composition[F[_], A, B, C](fa: F[A], ab: A => B, bc: B => C)(implicit F: Functor[F], eql: (F[C], F[C]) => Boolean): Prop = {
    eql(F.fmap(bc) { F.fmap(ab) { fa } }, F.fmap(bc compose ab) { fa })
  }
}

