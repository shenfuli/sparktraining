package org.training.spark

import scala.math.random
import org.apache.spark.{SparkConf, SparkContext}

/**
  * compute pi
  * Created by fuli.shen on 2016/10/25.
  */
object SparkPi {

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("Spark Pi")
    val spark = new SparkContext(conf)
    val slices = if (args.length > 0) args(0).toInt else 2
    val n = math.min(100000L * slices, Int.MaxValue).toInt // avoid overflow
    val count = spark.parallelize(1 until n, slices).map { i =>
        val x = random * 2 - 1
        val y = random * 2 - 1
        if (x * x + y * y < 1) 1 else 0
      }.reduce(_ + _)
    println("Pi is roughly " + 4.0 * count / n)//Pi is roughly 3.14248
    spark.stop()
  }
}