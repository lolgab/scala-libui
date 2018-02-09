package scalaui

import scala.scalanative.native.{CFunctionPtr0, CFunctionPtr2, Ptr, sizeof, stdlib}
import scalaui.collection._
import scalaui.ui._

class FontButton private (changeFont: CFunctionPtr2[Ptr[uiFontButton], Ptr[Byte], Unit],
                          onFontChange: CFunctionPtr0[Unit])
    extends Component {
  var createdFonts: Ptr[PointerList[uiDrawTextFont]] = null
//    stdlib.malloc(sizeof[PointerList[uiDrawTextFont]]).cast[Ptr[PointerList[uiDrawTextFont]]]

  def font: Font = {
    val f = new Font("", 0)
    f.control = uiFontButtonFont(control)
    createdFonts = f.control :: createdFonts
    f
  }

  def this(onFontChange: CFunctionPtr0[Unit] = doNothing _) =
    this(FontButton.changeFont _, onFontChange)

  override private[scalaui] def build(): Unit = {
    control = uiNewFontButton()
    uiFontButtonOnChanged(control, changeFont, onFontChange.cast[Ptr[Byte]])
  }

  private[scalaui] override def free(): Unit = {
    var l = createdFonts
    while (l != null) {
      uiDrawFreeTextFont(l.head)
      l = l.tail
    }
    createdFonts.free()
  }
}

object FontButton {
  private def changeFont(b: Ptr[uiFontButton], data: Ptr[Byte]): Unit = {
    val onChange = data.cast[CFunctionPtr0[Unit]]
    onChange()
  }
}
