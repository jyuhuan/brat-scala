package me.yuhuan.brat

/**
  * @author Yuhuan Jiang (jyuhuan@gmail.com).
  */
object Config {
  private var _BratPath = ""

  def BratPath_=(s: String) = _BratPath = s

  def BratPath: String = {
    if (_BratPath == "") {
      throw new Exception(
        """
          |> Set brat's path at Config.BratPath.
          |> For example:
          |>   Config.BratPath = "/Users/your/path/to/brat/"
        """.stripMargin)
    }
    _BratPath
  }
}
