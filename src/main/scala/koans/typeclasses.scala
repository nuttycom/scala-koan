package koans

trait Functor[F[_]] {
  def fmap[A, B](f: A => B): F[A] => F[B]
}

trait Monad[F[_]] extends Functor[F] {
  def point[A](a: A): F[A]
  def bind[A, B](f: A => F[B]): F[A] => F[B]
  def fmap[A, B](f: A => B): F[A] => F[B] = bind(f andThen point)
}

