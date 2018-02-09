package scalaui

import scala.scalanative.native.CFunctionPtr0
import ui._

class AboutMenuItem(onClick: CFunctionPtr0[Unit]) extends AbstractMenuItem {
  private[scalaui] def build(): Unit = {
    uiMenuItemOnClicked(control, onClick, null)
  }
}