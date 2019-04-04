package laaki

import java.util.Random
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.math.BigInteger

enum class Sarake {
    _1, _2, _3, PA, PY

}

fun sarakeNumerosta(i: Int) = when (i) {
    1 -> Sarake._1
    2 -> Sarake._2
    3 -> Sarake._3
    4 -> Sarake.PA
    else -> Sarake.PY
}

fun IntRange.random() = Random().nextInt((endInclusive + 1) - start) + start

class Tulos(sarake: Sarake, merkintä: Merkintä, nopat: List<Int>, heitto: Int) {
    val sarake = sarake
    val merkintä = merkintä
    val nopat = nopat
    val heitto = heitto

    fun pisteet() = merkintä.pisteetHeitolla(nopat)
}

class Peli() {

    val nopat = Nopat()
    val lappu = Lappu()

    var aloitettu = false
    var heitto = 0
    var kierros = 0

    var edellinen: Tulos? = null

    fun aloita() {
        nopat.heitä();
        aloitettu = true
        heitto = 1
        kierros = 1
        println("Peli alkoi. Eka heitto: ${nopat.tulostaNopat()}")
    }

    fun vaihdaLukitus(i: Int): Boolean {
        if (heitto != 0 && voiHeittää()) {
            nopat.vaihdaLukitus(i)
            return true
        }
        return false
    }

    fun heitä() {
        if (voiHeittää()) {
            nopat.heitä()
            nopat.vapauta()
            heitto++
            println("Kierros ${kierros}, heitto ${heitto}: ${nopat.tulostaNopat()}")
        }
    }

    fun merkitse(sarake: Sarake, merkattava: YläkerranMerkintä): Boolean {
        if (heitto == 0) return false
        if ((sarake == Sarake._1 && heitto > 1) || (sarake == Sarake._2 && heitto > 2)) return false
        val tulos = Tulos(sarake, merkattava, nopat.annaNopat(), heitto)
        if (lappu.lisääTulos(tulos)) {
            println("Kierros ${kierros}, sarake ${sarake}, merkitään tulos ${tulos.pisteet()} nopilla ${nopat.tulostaNopat()}")
            kierros++
            heitto = 0
            nopat.vapauta()
            edellinen = tulos
            return true
        }
        return false
    }

    fun merkitse(sarake: Sarake, merkattava: AlakerranMerkintä): Boolean {
        if (heitto == 0) return false
        if ((sarake == Sarake._1 && heitto > 1) || (sarake == Sarake._2 && heitto > 2)) return false
        val tulos = Tulos(sarake, merkattava, nopat.annaNopat(), heitto)
        if (lappu.lisääTulos(tulos)) {
            println("Kierros ${kierros}, sarake ${sarake}, merkitään tulos ${tulos.pisteet()} nopilla ${nopat.tulostaNopat()}")
            kierros++
            heitto = 0
            nopat.vapauta()
            edellinen = tulos
            return true
        }
        return false
    }

    fun peru(): Boolean {
        if (edellinen == null) println("Nykyinen puuttuu")
        if (lappu.peru(edellinen)) {
            heitto = edellinen!!.heitto
            kierros--
            edellinen = null
            println("Peruttiin merkintä")
            return true
        }
        println("Merkintää ei voi perua")
        return false
    }

    fun voiHeittää() = lappu.voiHeittää(heitto)

    fun pisteet() = lappu.pisteet()

    fun loppu() = lappu.täynnä()

}

class Noppa(id: Int) {
    val id = id
    var luku = 0
    var lukittu = false
    var näyttö = "---"

    fun heitä() {
        if (!lukittu) {
            luku = (1..6).random()
            näyttö = kerroLuku()
        }
    }

    fun lukitse() {
        lukittu = true
    }

    fun vapauta() {
        lukittu = false
    }

    fun vaihdaLukitus() {
        lukittu = !lukittu
    }

    fun kerroLuku() = when (luku) {
        in 1..6 -> luku.toString()
        else -> "---"
    }
}

class Nopat {
    val nopat = listOf(Noppa(0), Noppa(1), Noppa(2), Noppa(3), Noppa(4))

    fun heitä() {
        for (noppa in nopat) noppa.heitä()
    }

    fun vaihdaLukitus(i: Int) {
        nopat[i].vaihdaLukitus()
    }

    fun lukitse(indeksit: List<Int>) {
        for (noppa in nopat) {
            if (indeksit.contains(noppa.id)) {
                noppa.lukitse()
            }
        }
    }

    fun vapauta() {
        for (noppa in nopat) {
            noppa.vapauta()
        }
    }

    fun annaNopat() = nopat.map { it.luku }

    fun tulostaNopat() = nopat.map { "${it.kerroLuku()}" }.joinTo(StringBuilder()).toString()

}

class Lappu {
    val alakerrat: Map<Sarake, Alakerta> = Sarake.values().associateBy({ it }, { Alakerta(it) })

    val yläkerrat: Map<Sarake, Yläkerta> = Sarake.values().associateBy({ it }, { Yläkerta(it) })

    fun pisteet() = alakerrat.map { it.component2().pisteet() }.sum() + yläkerrat.map { it.component2().pisteet() }.sum()

    fun lisääTulos(tulos: Tulos): Boolean {
        if (tulos.sarake == Sarake.PA && vuorossaAlas() != tulos.merkintä) {
            return false
        }
        if (tulos.sarake == Sarake.PY && vuorossaYlös() != tulos.merkintä) {
            return false
        }
        return when (tulos.merkintä) {
            is AlakerranMerkintä -> alakerrat.get(tulos.sarake)?.lisääTulos(tulos.merkintä, tulos.nopat) ?: false
            is YläkerranMerkintä -> yläkerrat.get(tulos.sarake)?.lisääTulos(tulos.merkintä, tulos.nopat) ?: false
            else -> false
        }
    }

    fun voiHeittää(heitto: Int) = when (heitto) {
        0 -> true
        1 -> vapaata(listOf(Sarake._2, Sarake._3, Sarake.PA, Sarake.PY))
        2 -> vapaata(listOf(Sarake._3, Sarake.PA, Sarake.PY))
        else -> false
    }

    fun vapaata(sarakkeet: List<Sarake>) = vapaataYlhäällä(sarakkeet) || vapaataAlhaalla(sarakkeet)

    fun vapaataYlhäällä(sarakkeet: List<Sarake>) = yläkerrat.any { (a, b) -> sarakkeet.contains(a) && b.onVapaata() }

    fun vapaataAlhaalla(sarakkeet: List<Sarake>) = alakerrat.any { (a, b) -> sarakkeet.contains(a) && b.onVapaata() }

    fun peru(tulos: Tulos?): Boolean {
        if (tulos == null) return false
        val alakertaan = tulos.merkintä as? AlakerranMerkintä
        if (alakertaan != null) {
            return peru(tulos.sarake, alakertaan)
        }
        val yläkertaan = tulos.merkintä as? YläkerranMerkintä
        if (yläkertaan != null) {
            return peru(tulos.sarake, yläkertaan)
        }

        return false
    }

    fun peru(sarake: Sarake, merkintä: AlakerranMerkintä) = alakerrat.get(sarake)?.peru(merkintä) ?: false
    fun peru(sarake: Sarake, merkintä: YläkerranMerkintä) = yläkerrat.get(sarake)?.peru(merkintä) ?: false

    fun täynnä() = !(alakerrat.any { it.component2().onVapaata() } || yläkerrat.any { it.component2().onVapaata() })

    fun vuorossaAlas(): Merkintä? {
        var h = yläkerrat.get(Sarake.PA)?.vuorossaAlas()
        return h ?: alakerrat.get(Sarake.PA)?.vuorossaAlas()
    }

    fun vuorossaYlös(): Merkintä? {
        var h = alakerrat.get(Sarake.PY)?.vuorossaYlös()
        return h ?: yläkerrat.get(Sarake.PY)?.vuorossaYlös()
    }
}

class Alakerta(sarake: Sarake) {
    val sarake = sarake

    var tulokset: List<AlakerranMerkintä> = listOf(Pari(), KaksiParia(), Kolmiluku(), Neliluku(), Pikkusuora(), Isosuora(), Summa(), Mökki(), Jatsi())

    fun annaTulos(heitto: AlakerranMerkintä) = tulokset[heitto.järjestys - 7]

    fun lisääTulos(heitto: AlakerranMerkintä, nopat: List<Int>) = (tulokset[heitto.järjestys - 7].syötäNopat(nopat))

    fun pisteet() = tulokset.fold(0) { sum, acc -> sum + acc.pisteet() }

    fun onVapaata() = tulokset.any { it.vapaa() }

    fun vuorossaAlas(): AlakerranMerkintä? {
        for (i in 0..tulokset.count() - 1) {
            if (tulokset[i].vapaa()) {
                return tulokset[i]
            }
        }
        var jookos = false
        return when (jookos) {
            true -> null
            false -> tulokset[0]
        }
    }

    fun vuorossaYlös(): AlakerranMerkintä? {
        for (i in (tulokset.count() - 1) downTo 0) {
            if (tulokset[i].vapaa()) {
                return tulokset[i]
            }
        }
        return null
    }

    fun peru(poistettava: AlakerranMerkintä): Boolean {
        return tulokset[poistettava.järjestys - 7].nollaa()
    }

}

abstract class Merkintä() {
    abstract val järjestys: Int
    abstract fun pisteetHeitolla(nopat: List<Int>): Int
    abstract fun otsake(): String
    var hp = ""

    var tulos by property(hp)
    fun tulosProperty() = Merkintä::hp

    val otsikko: String
        get() = otsake()

    lateinit var nopat: List<Int>

    fun pisteet() = if (::nopat.isInitialized && !nopat.isEmpty()) heitonPisteet() else 0

    fun heitonPisteet() = pisteetHeitolla(nopat)

    fun syötäNopat(luvut: List<Int>): Boolean {
        if (::nopat.isInitialized && !nopat.isEmpty()) {
            println("Nopat on jo syötetty")
            return false
        }
        nopat = luvut
        hp = merkintä()
        return true
    }

    fun merkintä() = if (!vapaa()) pisteet().toString() else ""
    fun vapaa() = !(::nopat.isInitialized) || nopat.isEmpty()
    fun nollaa(): Boolean {
        nopat = listOf()
        hp = merkintä()
        return true
    }

}

abstract class AlakerranMerkintä() : Merkintä() {

}


class Pari() : AlakerranMerkintä() {
    override val järjestys = 7
    override fun pisteetHeitolla(nopat: List<Int>): Int {
        val osat = nopat.groupBy { it }
        val luku = osat.filter { it.component2().count() > 1 }.maxBy { it.component1() }

        return 2 * (luku?.component1() ?: 0)
    }

    override fun otsake() = "Pari"
}

class KaksiParia() : AlakerranMerkintä() {
    override val järjestys = 8
    var eka = 0
    var toka = 0

    override fun pisteetHeitolla(nopat: List<Int>): Int {
        val osat = nopat.groupBy { it }.filter { it.component2().count() > 1 }
        if (osat.count() != 2) {
            return 0
        }
        var summa = 0
        for (osa in osat) {
            summa += 2 * osa.component1()
        }
        return summa
    }

    override fun otsake() = "Kaksi paria"
}

class Kolmiluku() : AlakerranMerkintä() {
    override val järjestys = 9
    override fun pisteetHeitolla(nopat: List<Int>): Int {
        val osat = nopat.groupBy { it }
        val luku = osat.filter { it.component2().count() > 2 }.maxBy { it.component1() }

        return 3 * (luku?.component1() ?: 0)
    }

    override fun otsake() = "Kolmiluku"
}

class Neliluku() : AlakerranMerkintä() {
    override val järjestys = 10
    override fun pisteetHeitolla(nopat: List<Int>): Int {
        val osat = nopat.groupBy { it }
        val luku = osat.filter { it.component2().count() > 3 }.maxBy { it.component1() }

        return 4 * (luku?.component1() ?: 0)
    }

    override fun otsake() = "Neliluku"
}

abstract class Suora() : AlakerranMerkintä() {
    abstract val a: Int
    abstract val b: Int

    fun onKaikki(a: Int, b: Int, luvut: List<Int>): Boolean {
        for (i in a..b) {
            if (!luvut.any { it == i }) return false
        }
        return true

    }

    override fun pisteetHeitolla(nopat: List<Int>): Int {
        if (onKaikki(a, b, nopat)) {
            return nopat.sum()
        }
        return 0
    }
}

class Pikkusuora() : Suora() {
    override val järjestys = 11
    override val a = 1
    override val b = 5
    override fun otsake() = "Pikkusuora"
}

class Isosuora() : Suora() {
    override val järjestys = 12
    override val a = 2
    override val b = 6
    override fun otsake() = "Isosuora"
}

class Summa() : AlakerranMerkintä() {
    override val järjestys = 13
    override fun pisteetHeitolla(nopat: List<Int>) = nopat.sum()
    override fun otsake() = "Summa"
}

class Mökki() : AlakerranMerkintä() {
    override val järjestys = 14
    override fun pisteetHeitolla(nopat: List<Int>): Int {
        val osat = nopat.groupBy { it }
        if (osat.count() != 2) {
            return 0
        }
        var summa = 0
        var onKaksi = false
        var onKolme = false
        for (osa in osat) {
            if (osa.component2().count() == 2) {
                onKaksi = true
            }
            if (osa.component2().count() == 3) {
                onKolme = true
            }
            summa += osa.component2().sum()
        }
        return if (onKaksi && onKolme) summa else 0

    }

    override fun otsake() = "Mökki"
}

class Jatsi() : AlakerranMerkintä() {
    override val järjestys = 15
    override fun pisteetHeitolla(nopat: List<Int>): Int {
        if (nopat.groupBy { it }.count() == 1) {
            var summa = nopat.sum()
            if (summa > 0) {
                summa += 50
            }
            return summa
        }
        return 0;
    }

    override fun otsake() = "Jatsi"
}

abstract class YläkerranMerkintä : Merkintä() {
    abstract val luku: Int

    override fun pisteetHeitolla(nopat: List<Int>): Int {
        val määrä = nopat.filter { it == luku }.count()
        return luku * (määrä - 3)
    }

    override fun otsake() = "" + luku

}

class Ykköset : YläkerranMerkintä() {
    override val luku = 1
    override val järjestys = 1
}

class Kakkoset : YläkerranMerkintä() {
    override val luku = 2
    override val järjestys = 2
}

class Kolmoset : YläkerranMerkintä() {
    override val luku = 3
    override val järjestys = 3
}

class Neloset : YläkerranMerkintä() {
    override val luku = 4
    override val järjestys = 4
}

class Vitoset : YläkerranMerkintä() {
    override val luku = 5
    override val järjestys = 5
}

class Kutoset : YläkerranMerkintä() {
    override val luku = 6
    override val järjestys = 6
}


class Yläkerta(sarake: Sarake) {
    val sarake = sarake
    val tulokset = listOf(Ykköset(), Kakkoset(), Kolmoset(), Neloset(), Vitoset(), Kutoset())
    fun pisteet(): Int {
        if (tulokset.any { it.vapaa() }) {
            return 0
        }
        val summa = tulokset.sumBy { it.pisteet() }
        return if (summa >= 0) {
            50 + summa
        } else summa
    }

    fun lisääTulos(heitto: YläkerranMerkintä, nopat: List<Int>) = tulokset[heitto.järjestys - 1].syötäNopat(nopat)

    fun syötäNopat(i: Int, nopat: List<Int>) = tulokset[i - 1].syötäNopat(nopat)
    fun onVapaata() = tulokset.any { it.vapaa() }

    fun vuorossaAlas(): YläkerranMerkintä? {
        for (i in 0..5) {
            if (tulokset[i].vapaa()) {
                return tulokset[i]
            }
        }
        return null
    }

    fun vuorossaYlös(): YläkerranMerkintä? {
        for (i in 5 downTo 0) {
            if (tulokset[i].vapaa()) {
                return tulokset[i]
            }
        }
        return null
    }

    fun peru(poistettava: YläkerranMerkintä): Boolean {
        return tulokset[poistettava.järjestys - 1].nollaa()
    }
}

