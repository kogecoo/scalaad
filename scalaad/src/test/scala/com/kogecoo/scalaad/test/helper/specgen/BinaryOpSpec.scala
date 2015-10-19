package com.kogecoo.scalaad.test.helper.specgen

import com.kogecoo.scalaad.graph.Node
import com.kogecoo.scalaad.test.helper.matcher.ValueMatcherProp._
import com.kogecoo.scalaad.test.helper.gen.{GenNode, GenValue}
import com.kogecoo.scalaad.rule.{Value, ValueRule}
import com.kogecoo.scalaad.test.helper.rule.CompareRule

import org.scalacheck.Prop
import org.scalacheck.Prop.forAll
import scala.language.higherKinds


class BinaryOpSpec[U[_], T](d: BinaryOpSpecDef[U, T], nodes: GenNode[U, T], values: GenValue[U, T])(implicit rule: ValueRule[U, T], compare: CompareRule[U, T]) {

  type Restriction = T => Boolean

  lazy val default = (_: T) => true

  def n(r: Restriction = _ => true) = nodes.genNode(r)
  def v(r: Restriction = _ => true) = values.genValue(r)

  def apply(restrictionLeft: Restriction = default, restrictionRight: Restriction = default): Prop = {
    forAll(n(restrictionLeft), n(restrictionRight)) { (a: Node[U, T], b: Node[U, T]) =>
      d.op(a, b).apply() shouldBe d.applyExpectation(a, b)
    }
  }

  def derivWrtSelf(restriction: Restriction = default): Prop = {
    forAll(n(restriction)) { (a: Node[U, T]) =>
      d.op(a, a).deriv(a) shouldBe d.derivWrtSelfExpectation(a)
    }
  }

  def derivWrtLeft(restrictionLeft: Restriction = default, restrictionRight: Restriction = default): Prop = {
    forAll(n(restrictionLeft), n(restrictionRight)) { (a: Node[U, T], b: Node[U, T]) =>
      d.op(a, b).deriv(a) shouldBe d.derivWrtLeftExpectation(a, b)
    }
  }

  def derivWrtRight(restrictionleft: Restriction = default, restrictionRight: Restriction = default): Prop = {
    forAll(n(restrictionleft), n(restrictionRight)) { (a: Node[U, T], b: Node[U, T]) =>
      d.op(a, b).deriv(b) shouldBe d.derivWrtRightExpectation(a, b)
    }
  }

  def deriv(restrictionLeft: Restriction = default, restrictionRight: Restriction = default): Prop = {
    forAll(n(restrictionLeft), n(restrictionRight), n()) { (a: Node[U, T], b: Node[U, T], c: Node[U, T]) =>
      d.op(a, b).deriv(c) shouldBe d.derivExpectation(a, b, c)
    }
  }

  def propagate(restrictionLeft: Restriction = default, restrictionRight: Restriction = default, restrictionValue: Restriction = default): Prop = {
    forAll(n(restrictionLeft), n(restrictionRight), v(restrictionValue)) { (a: Node[U, T], b: Node[U, T], c: Value[U, T]) =>
      d.op(a, b).propagate(c) shouldBe d.propagateExpectation(a, b, c)
    }
  }

  def grad(restrictionLeft: Restriction = default, restrictionRight: Restriction = default): Prop = {
    forAll(n(restrictionLeft), n(restrictionRight)) { (a: Node[U, T], b: Node[U, T]) =>
      d.op(a, b).grad() shouldBe d.gradExpectation(a, b)
    }
  }

}