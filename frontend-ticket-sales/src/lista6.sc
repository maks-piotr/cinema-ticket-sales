// Maksymilian Piotrowski

// zadanie 1
enum BT[+A]:
  case Empty
  case Node(elem: A, left: BT[A], right: BT[A])
import BT.*

import scala.annotation.tailrec
val tt =
  Node(1,Node(2,Node(4,Empty,Empty),Empty),Node(3,Node(5,Empty,Node(6,Empty,Empty)),Empty))

// zadanie 1a
val sumBT: BT[Int] => Int =
  bt =>
    bt match
      case Empty => 0
      case Node(elem, lbt, rbt) => elem + sumBT(lbt) + sumBT(rbt)

sumBT(Empty) == 0
sumBT(tt) == 21

// zadanie 1b
def foldBT[A, B](f: A => (B, B) => B)(acc: B)(bt: BT[A]): B =
  bt match
    case Empty => acc
    case Node(elem, tl, tr) => f(elem)(foldBT(f)(acc)(tl), foldBT(f)(acc)(tr))

// zadanie 1c
val sumBTfold: BT[Int] => Int =
  bt =>
    foldBT[Int,Int](x => (y,z) => x+y+z)(0)(bt)

sumBTfold(Empty) == 0
sumBTfold(tt) == 21

// zadanie 2
@tailrec
def whileLoop(warunek: => Boolean)(wyrazenie: => Unit): Unit =
  if warunek then
    wyrazenie
    whileLoop(warunek)(wyrazenie)

var count = 0
while (count < 5) {
  println(count)
  count += 1
}

count = 0
whileLoop (count < 5) {
  println(count)
  count += 1
}

// zadanie 3
enum lBT[+A]:
  case LEmpty
  case LNode(elem: A, left: () => lBT[A], right: () => lBT[A])

import lBT.*

// zadanie 3a
def lBreadth[A](lbt: lBT[A]): LazyList[A] =
  def lBreadthHelp(queue: List[lBT[A]]): LazyList[A] =
    queue match
      case Nil => LazyList()
      case LEmpty :: t => lBreadthHelp(t)
      case LNode(elem, llbt, rlbt) :: t =>
        elem #:: lBreadthHelp(t ::: List(llbt(), rlbt()))
  lBreadthHelp(List(lbt))

// zadanie 3b
def BT2lBT[A](bt: BT[A]): lBT[A] =
  foldBT[A, lBT[A]](v => (lbt, rbt) => LNode(v, () => lbt, () => rbt))(LEmpty)(bt)

val tt2 = Node("a",Node("b",Node("d",Empty,Empty),Empty),Node("c",Node("e",Empty,Node("f",Empty,Empty)),Empty))
lBreadth(BT2lBT(tt)).force == LazyList(1, 2, 3, 4, 5, 6)
lBreadth(LEmpty) == LazyList()
lBreadth(BT2lBT(tt2)).force == LazyList("a", "b", "c", "d", "e", "f")

// zadanie 3c
def lTree(n : Int) : lBT[Int] =
  LNode(n, () => lTree(2*n), () => lTree(2*n+1))

lBreadth(lTree(1)).take(20).toList == List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20)
lBreadth(LEmpty).take(20).toList == List()
lBreadth(lTree(23)).take(3).toList == List(23,46,47)
lBreadth(lTree(10)).take(7).toList == List(10,20,21,40,41,42,43)