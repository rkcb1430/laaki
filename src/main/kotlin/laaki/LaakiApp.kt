package laaki

import javafx.geometry.Pos
import tornadofx.*
import javafx.scene.text.Font
import javafx.scene.control.Label
import javafx.scene.text.Text
import javafx.scene.control.Alert.AlertType
import javafx.scene.paint.Color
import javafx.scene.control.Button
import javafx.beans.Observable
import javafx.scene.control.TableView

class LaakiController() : Controller() {
	var peli = Peli()
}

class LaakiView : View() {
	val controller: LaakiController by inject()

	var noppaButton: List<Button> by singleAssign()

	var tulosLabel = label("Pisteet")
	var pisteetText = text("0")
	var kierrosText = text("0")
	var heittoText = text("0")

	var viimeisinMerkintä: Merkintä? = null
	var viimeisinHeittoja = 0

	val alakerrat = controller.peli.lappu.alakerrat.map({ (_, b) -> b.tulokset.observable() })
	val yläkerrat = controller.peli.lappu.yläkerrat.map({ (_, b) -> b.tulokset.observable() })


	override val root = vbox {
		prefWidth = 1024.0
		prefHeight = 768.0
		alignment = Pos.CENTER

		vbox {
			vbox {
				hbox {
					var i = 0
					for (yläkerta in yläkerrat) {
						i++
						tableview(yläkerta) {
							val j = i
							columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY
							readonlyColumn("Heitto", YläkerranMerkintä::otsikko)
							column("Pisteet", YläkerranMerkintä::hp)

							onUserSelect { tulos ->
								if (controller.peli.merkitse(sarakeNumerosta(j), tulos)) {
									viimeisinMerkintä = tulos
									viimeisinHeittoja = j
									pisteetText.text = "" + controller.peli.pisteet()
									for (nb in noppaButton) {
										nb.textFill = Color.BLACK
									}
									refresh()
									if (controller.peli.loppu()) {
										alert(AlertType.INFORMATION, "Loppu!")
									}
								} else alert(AlertType.INFORMATION, "Tulosta ei voi merkata tähän")
							}
						}
					}

				}
				hbox {
					var i = 0
					for (alakerta in alakerrat) {
						i++
						tableview(alakerta) {
							val j = i
							columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY
							readonlyColumn("Heitto", AlakerranMerkintä::otsikko)
							column("$j Pisteet", AlakerranMerkintä::hp)
							onUserSelect { tulos ->
								if (controller.peli.merkitse(sarakeNumerosta(j), tulos)) {
									viimeisinMerkintä = tulos
									viimeisinHeittoja = j
									pisteetText.text = "" + controller.peli.pisteet()
									for (nb in noppaButton) {
										nb.textFill = Color.BLACK
									}
									refresh()
									if (controller.peli.loppu()) {
										alert(AlertType.INFORMATION, "Loppu!")
									}
								} else alert(AlertType.INFORMATION, "Tulosta ei voi merkata tähän")
							}
						}
					}


				}
			}
			hbox {
				hboxConstraints {
					marginLeft = 5.0
				}
				hbox {
					hboxConstraints {
						marginLeft = 5.0
					}
					noppaButton = (1..5).map {
						button(controller.peli.nopat.nopat[it - 1].näyttö) {
							font = Font(20.0)
							textFill = Color.BLACK
							action {
								if (controller.peli.vaihdaLukitus(it - 1)) {
									if (textFill == Color.RED) {
										textFill = Color.BLACK
									} else {
										textFill = Color.RED
									}
								}
							}
						}
					}

				}


				button("Aloita!") {
					action {
						if (!controller.peli.aloitettu) {
							controller.peli.aloita()
							text = "Heitä!"
						} else {
							controller.peli.heitä()
						}

						for (noppa in controller.peli.nopat.nopat) {
							noppaButton[noppa.id].text = noppa.näyttö
							noppaButton[noppa.id].textFill = Color.BLACK
						}

						kierrosText.text = "" + controller.peli.kierros
						heittoText.text = "" + controller.peli.heitto
					}
				}
				button("Peru") {
					action {
						if (controller.peli.peru()) {
							pisteetText.text = "" + controller.peli.pisteet()
							kierrosText.text = "" + controller.peli.kierros
							heittoText.text = "" + controller.peli.heitto
						}
					}

				}

			}
			hbox {
				tulosLabel = label("Pisteet") { font = Font(20.0) }
				pisteetText = text("0") { font = Font(20.0) }
			}
			vbox {
				hbox {
					label("Kierros") { font = Font(15.0) }
					kierrosText = text("0") { font = Font(15.0) }
				}
				hbox {
					label("Heitto") { font = Font(15.0) }
					heittoText = text("0") { font = Font(15.0) }
				}
			}
		}
	}
}
