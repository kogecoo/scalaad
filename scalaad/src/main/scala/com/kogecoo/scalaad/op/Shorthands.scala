package com.kogecoo.scalaad.op

import com.kogecoo.scalaad.graph.{Apply1, Half, One, Unsafe, V, Zero}
import shapeless.Nat


object Shorthands {

  object Const {

    def half[N <: Nat](v: V[N]): V[N] = Half[N](v.shape)

    def one[N <: Nat](v: V[N]): V[N] = One[N](v.shape)

    def zero[N <: Nat](v: V[N]): V[N] = Zero[N](v.shape)

  }

  object Math {

    // unary

    def sin[N <: Nat](v: V[N]): V[N] = Apply1[N](v, Sin)

    def cos[N <: Nat](v: V[N]): V[N] = Apply1[N](v, Cos)

    def tan[N <: Nat](v: V[N]): V[N] = Apply1[N](v, Tan)

    def asin[N <: Nat](v: V[N]): V[N] = Apply1[N](v, Asin)

    def acos[N <: Nat](v: V[N]): V[N] = Apply1[N](v, Acos)

    def atan[N <: Nat](v: V[N]): V[N] = Apply1[N](v, Atan)

    def sinh[N <: Nat](v: V[N]): V[N] = Apply1[N](v, Sinh)

    def cosh[N <: Nat](v: V[N]): V[N] = Apply1[N](v, Cosh)

    def tanh[N <: Nat](v: V[N]): V[N] = Apply1[N](v, Tanh)

    def ln[N <: Nat](v: V[N]): V[N] = Apply1[N](v, Ln)

    def exp[N <: Nat](v: V[N]): V[N] = Apply1[N](v, Exp)

    def sqrt[N <: Nat](v: V[N]): V[N] = Apply1[N](v, Sqrt)


    // binary

    def pow[L <: Nat, R <: Nat](l: V[L], r: V[R]): V[_ <: Nat] = Unsafe.apply2(l, r, Pow)


  }
}
