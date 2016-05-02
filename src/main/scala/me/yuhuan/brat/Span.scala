package me.yuhuan.brat

/**
  * @author Yuhuan Jiang (jyuhuan@gmail.com).
  */

/**
  * A span. E.g., a span of text. Left inclusive, right exclusive.
  */
trait Span { self =>
  def start: Int
  def end: Int
  def length = end - start

  def subsumes(that: Span) = this.start <= that.start && this.end >= that.end
  def overlaps(that: Span) = this.start <= that.end && that.start < this.end
  def neighbors(that: Span) = this.start == that.end || this.end == that.start

  def intersect(that: Span): Span = {
    if (this overlaps that) {
      val newStart = Math.max(this.start, that.start)
      val newEnd = Math.min(this.end, that.end)
      Span(newStart, newEnd)
    }
    else Span.Empty
  }
  def ⋂(that: Span): Span = this intersect that

  def union(that: Span): Option[Span] = {
    if ((this overlaps that) || (this neighbors that)) {
      val newStart = Math.min(this.start, that.start)
      val newEnd = Math.max(this.end, that.end)
      Some(Span(newStart, newEnd))
    }
    else None
  }
  def ⋃(that: Span) = this union that

  /**
    * [1,4) ⋃ [7,9) = {1,2,3,7,8}
    * @param that
    * @return
    */
  def setUnion(that: Span): Set[Int] = this.asSet intersect that.asSet

  def overlapInUnion(that: Span) = {
    this ⋃ that match {
      case Some(u) =>
        val intersect = this ⋂ that
        intersect.length.toDouble / u.length.toDouble
      case None => 0.0
    }
  }

  def map(f: Int ⇒ Int): Span = Span(f(start), f(end))
  def shiftBy(Δ: Int) = this.map(_ + Δ)

  def asSet = new Set[Int] { inner =>
    def contains(x: Int): Boolean = x >= start && x < end
    def +(x: Int): Set[Int] = self setUnion Span(x, x + 1)
    def -(x: Int): Set[Int] = Span(start, x) setUnion Span(x + 1, end)
    def iterator: Iterator[Int] = new Iterator[Int] {
      var cur = start
      def hasNext: Boolean = cur != end
      def next(): Int = {
        val old = cur
        cur += 1
        old
      }
    }
  }

  override def toString = s"[$start,$end)"
}

object Span {

  def apply(_start: Int, _end: Int): Span = new Span {
    def start: Int = _start
    def end: Int = _end
  }

  object Empty extends Span { self =>
    def start: Int = throw new Exception("The empty span doesn't have a start!")
    def end: Int = throw new Exception("The empty span doesn't have an end!")
    override def length = 0
    override def subsumes(that: Span) = false
    override def intersect(that: Span) = self
    override def union(that: Span) = Some(that)
    override def overlapInUnion(that: Span) = 0.0
  }

}