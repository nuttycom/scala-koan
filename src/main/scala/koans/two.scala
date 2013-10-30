package koans

package object two {
  type Id[X] = X

  implicit val IdMonad: Monad[Id] = new Monad[Id] {
    def point[A](a: A): Id[A] = ???
    def bind[A, B](f: A => Id[B]): Id[A] => Id[B] = ???
  }
}



