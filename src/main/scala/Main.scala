import scala.io.Source
import java.io.PrintWriter

object Main {
  def LSC(ss1: String, ss2: String) = {
    val s1 = (ss1 split '　').foldLeft(""){_ + _}
    val s2 = (ss2 split '　').foldLeft(""){_ + _}
    val size1 = s1.length
    val size2 = s2.length
    var arr: Array[Array[Int]] = Array.ofDim(size1+1,size2+1)
    // 初期化
    arr(0)(0) = 0
    for{
      i <- (1 to size1)
      j <- (1 to size2)
    } yield {
      arr(i)(0) = i
      arr(0)(j) = j
    }
    // 動的計画法　行列を埋める
      (1 to size1).map{ i =>
        (1 to size2).map{ j =>
          arr(i)(j) = List(
            arr(i-1)(j)+1,
            arr(i)(j-1)+1,
            arr(i-1)(j-1) + 3*(1-delta(s1(i-1), s2(j-1)))
          ).min
        }
      }
    /* 行列の表示
      (0 to size1).map{ i =>
        (0 to size2).map{ j =>
          if(arr(i)(j) < 10)
            print(arr(i)(j).toString + " ")
          else
            print(arr(i)(j))
          print(":")
        }
        println("")
      }
     */
    arr(size1)(size2)
  }

  def delta(s1: Char, s2: Char) = {
    if (s1 == s2) 1
    else 0
  }

  def getWakas(src: String) = {
    var source = Source.fromFile(src)
    val lines = source.getLines.toList
    val wakas = lines.map{line =>
      Map[String, String](
        "id" -> (line split ' ').head,
        "waka" -> ((line split ' ').tail.tail.tail).foldLeft("")(_ + _)
      )
    }
    source.close
    wakas
  }

  def main(args: Array[String]) = {
    /*
    val s1 = readLine
    val s2 = readLine
    val result = LSC(s1, s2)
    println("result: " + result.toString)
     */
    // 和歌読み込み
    val wakas = getWakas("sinkokinn.txt").take(99)
    // println(wakas.length)
    // 和歌全てと和歌全ての直積
    val waka_pairs_tmp = for {
      w1 <- wakas
      w2 <- wakas
    } yield {
      (w1, w2)
    }
    val waka_pairs = waka_pairs_tmp.filter{x => x._1("id") != x._2("id")}
    val distances = waka_pairs.map(w => LSC(w._1("waka"), w._2("waka")))
    val results = waka_pairs.map{x=>(x._1("id"), x._2("id"))} zip distances
    //ファイルに出力
    val file = new PrintWriter("shinkokin_distances.csv")
    file.write("waka_id,waka_id,distance\n")
    for (r <- results) yield{
      file.write(r._1._1 + "," + r._1._2 + "," + r._2 + "\n")
    }
    file.close
    // print
      for { i <- (0 to waka_pairs.length-1)} yield {
        println(waka_pairs(i)._1("id") + ", " + waka_pairs(i)._2("id") + ", " + distances(i).toString)
      }
    println()
  }
}
