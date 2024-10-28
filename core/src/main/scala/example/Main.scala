package example

import scala.scalanative.unsafe.{*, given}
import libncurses.all.*
import scala.util.control.Breaks
import scala.scalanative.unsigned.{UInt, given}

def NCURSES_MOUSE_MASK(b: Int, m: Long): CUnsignedInt =
  (m << (((b) - 1) * 5)).toUInt

inline val KEY_MOUSE = 409
inline val OK = 0

@main def main(args: String*): Unit =
  initscr()
  keypad(_nc_stdscr(), true)
  mousemask(mmask_t(NCURSES_MOUSE_MASK(6, 10L)), null)
  noecho()
  curs_set(0)
  mvprintw(12, 30, c"Hello World from Scala Native!")
  val mev = stackalloc[MEVENT]()
  var px = 0
  Breaks.breakable {
    while (true) {
      val c = getch()
      if c == 0x71 then Breaks.break
      if c == KEY_MOUSE then
        if getmouse(mev) == OK then
          clear()

          px = (!mev).x;
          if (px < 2) px = 2;
          if (px > 77) px = 77;
          mvprintw(23, px - 2, c"-----");
          refresh();
    }
  }
  endwin()
