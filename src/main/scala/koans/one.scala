package koans.one

sealed trait Maybe[+A] {
  import Maybe._
 
  /**
   * The single abstract method to be used for implementation of all remaining
   * methods. In each case, replace ??? with an implementation that satisfies
   * the type signiature.
   */
  def cata[X](some: A => X, none: => X): X

  def map[B](f: A => B): Maybe[B] = ???
  def flatMap[B](f: A => Maybe[B]): Maybe[B] = ???
  def getOrElse[AA >: A](e: => AA): AA = ???
  def filter(p: A => Boolean): Maybe[A] = ???
  def foreach(f: A => Unit): Unit = ???
  def isDefined: Boolean = ???
  def isEmpty: Boolean = ???
  def get: A = ???
  def orElse[AA >: A](o: Maybe[AA]): Maybe[AA] = ???
  def toLeft[X](right: => X): Either[A, X] = ???
  def toRight[X](left: => X): Either[X, A] = ???
  def toList: List[A] = ???
  def iterator: Iterator[A] = ???
  def equalTo[AA >: A](other: Maybe[AA])(implicit aeq: (AA, AA) => Boolean): Boolean = ???
}
 
/**
 * Companion object which provides constructors for the only two members of the
 * sum type 'Maybe.' No modifications should be made to this object.
 */
object Maybe {
  def none[A] = new Maybe[A] {
    def cata[X](s: A => X, n: => X) = n
  }
 
  def some[A](a: A) = new Maybe[A] {
    def cata[X](s: A => X, n: => X) = s(a)
  }
}



