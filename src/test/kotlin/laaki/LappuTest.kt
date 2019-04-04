package laaki

import laaki.Pari
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import sun.jvm.hotspot.HelloWorld.fib

class LappuTest {

    @Test
    fun testPari() {
        var pari = Pari()
        assertEquals(0, pari.pisteet())

        pari.syötäNopat(listOf(6, 1, 2, 3, 4))
        assertEquals(0, pari.pisteet())

        pari = Pari()
        pari.syötäNopat(listOf(1, 1, 2, 3, 4))
        assertEquals(2, pari.pisteet())

        pari = Pari()
        pari.syötäNopat(listOf(1, 1, 2, 3, 3))
        assertEquals(6, pari.pisteet())

        pari = Pari()
        pari.syötäNopat(listOf(1, 6, 6, 3, 6))
        assertEquals(12, pari.pisteet())
    }

    @Test
    fun testKolmiluku() {
        var kolmiluku = Kolmiluku()
        assertEquals(0, kolmiluku.pisteet())

        kolmiluku.syötäNopat(listOf(6, 1, 2, 3, 4))
        assertEquals(0, kolmiluku.pisteet())

        kolmiluku = Kolmiluku()
        kolmiluku.syötäNopat(listOf(6, 1, 1, 1, 4))
        assertEquals(3, kolmiluku.pisteet())

        kolmiluku = Kolmiluku()
        kolmiluku.syötäNopat(listOf(6, 1, 6, 6, 6))
        assertEquals(18, kolmiluku.pisteet())
    }

    @Test
    fun testNeliluku() {
        var neliluku = Neliluku()
        assertEquals(0, neliluku.pisteet())

        neliluku.syötäNopat(listOf(6, 1, 2, 3, 4))
        assertEquals(0, neliluku.pisteet())

        neliluku = Neliluku()
        neliluku.syötäNopat(listOf(1, 2, 1, 1, 1))
        assertEquals(4, neliluku.pisteet())

        neliluku = Neliluku()
        neliluku.syötäNopat(listOf(6, 6, 6, 6, 6))
        assertEquals(24, neliluku.pisteet())
    }

    @Test
    fun testKaksiParia() {
        var kaksiParia = KaksiParia()
        assertEquals(0, kaksiParia.pisteet())

        kaksiParia.syötäNopat(listOf(6, 1, 2, 3, 4))
        assertEquals(0, kaksiParia.pisteet())

        kaksiParia = KaksiParia()
        kaksiParia.syötäNopat(listOf(6, 1, 1, 1, 1))
        assertEquals(0, kaksiParia.pisteet())

        kaksiParia = KaksiParia()
        kaksiParia.syötäNopat(listOf(6, 6, 6, 6, 6))
        assertEquals(0, kaksiParia.pisteet())

        kaksiParia = KaksiParia()
        kaksiParia.syötäNopat(listOf(6, 6, 1, 1, 1))
        assertEquals(14, kaksiParia.pisteet())

        kaksiParia = KaksiParia()
        kaksiParia.syötäNopat(listOf(6, 5, 1, 5, 6))
        assertEquals(22, kaksiParia.pisteet())
    }

    @Test
    fun testPikkusuora() {
        var pikkusuora = Pikkusuora()
        assertEquals(0, pikkusuora.pisteet())

        pikkusuora.syötäNopat(listOf(6, 6, 6, 6, 6))
        assertEquals(0, pikkusuora.pisteet())

        pikkusuora = Pikkusuora()
        pikkusuora.syötäNopat(listOf(6, 5, 4, 3, 2))
        assertEquals(0, pikkusuora.pisteet())

        pikkusuora = Pikkusuora()
        pikkusuora.syötäNopat(listOf(1, 5, 6, 3, 2))
        assertEquals(0, pikkusuora.pisteet())

        pikkusuora = Pikkusuora()
        pikkusuora.syötäNopat(listOf(1, 2, 3, 4, 5))
        assertEquals(15, pikkusuora.pisteet())

        pikkusuora = Pikkusuora()
        pikkusuora.syötäNopat(listOf(1, 5, 4, 3, 2))
        assertEquals(15, pikkusuora.pisteet())
    }

    @Test
    fun testIsosuora() {
        var isosuora = Isosuora()
        assertEquals(0, isosuora.pisteet())

        isosuora.syötäNopat(listOf(6, 6, 6, 6, 6))
        assertEquals(0, isosuora.pisteet())

        isosuora = Isosuora()
        isosuora.syötäNopat(listOf(5, 4, 3, 2, 1))
        assertEquals(0, isosuora.pisteet())

        isosuora = Isosuora()
        isosuora.syötäNopat(listOf(6, 2, 3, 4, 5))
        assertEquals(20, isosuora.pisteet())

        isosuora = Isosuora()
        isosuora.syötäNopat(listOf(6, 5, 4, 3, 2))
        assertEquals(20, isosuora.pisteet())

        isosuora = Isosuora()
        isosuora.syötäNopat(listOf(1, 5, 6, 3, 2))
        assertEquals(0, isosuora.pisteet())
    }

    @Test
    fun testSumma() {
        var summa = Summa()
        assertEquals(0, summa.pisteet())

        summa.syötäNopat(listOf(0, 0, 0, 0, 0))
        assertEquals(0, summa.pisteet())

        summa = Summa()
        summa.syötäNopat(listOf(2, 3, 5, 3, 2))
        assertEquals(15, summa.pisteet())

        summa = Summa()
        summa.syötäNopat(listOf(1, 2, 3, 4, 5))
        assertEquals(15, summa.pisteet())

        summa = Summa()
        summa.syötäNopat(listOf(6, 6, 6, 6, 6))
        assertEquals(30, summa.pisteet())
    }

    @Test
    fun testMökki() {
        var mökki = Mökki()
        assertEquals(0, mökki.pisteet())

        mökki.syötäNopat(listOf(0, 0, 0, 0, 0))
        assertEquals(0, mökki.pisteet())

        mökki = Mökki()
        mökki.syötäNopat(listOf(2, 4, 3, 5, 2))
        assertEquals(0, mökki.pisteet())

        mökki = Mökki()
        mökki.syötäNopat(listOf(6, 6, 6, 6, 6))
        assertEquals(0, mökki.pisteet())

        mökki = Mökki()
        mökki.syötäNopat(listOf(1, 2, 1, 2, 1))
        assertEquals(7, mökki.pisteet())

        mökki = Mökki()
        mökki.syötäNopat(listOf(6, 6, 6, 5, 5))
        assertEquals(28, mökki.pisteet())

        mökki = Mökki()
        mökki.syötäNopat(listOf(6, 5, 5, 5, 5))
        assertEquals(0, mökki.pisteet())

        assertEquals(28, mökki.pisteetHeitolla(listOf(6, 6, 6, 5, 5)))
    }

    @Test
    fun testJatsi() {
        var jatsi = Jatsi()
        assertEquals(0, jatsi.pisteet())

        jatsi.syötäNopat(listOf(0, 0, 0, 0, 0))
        assertEquals(0, jatsi.pisteet())

        jatsi = Jatsi()
        jatsi.syötäNopat(listOf(6, 6, 5, 6, 6))
        assertEquals(0, jatsi.pisteet())

        jatsi = Jatsi()
        jatsi.syötäNopat(listOf(1, 1, 1, 1, 1))
        assertEquals(55, jatsi.pisteet())
    }

    @Test
    fun testYläkerranMerkintä() {
        var yk1 = Ykköset()
        assertEquals(0, yk1.pisteet())

        yk1.syötäNopat(listOf(0, 0, 0, 0, 0))
        assertEquals(-3, yk1.pisteet())

        yk1 = Ykköset()
        yk1.syötäNopat(listOf(6, 5, 3, 2, 2))
        assertEquals(-3, yk1.pisteet())

        yk1 = Ykköset()
        yk1.syötäNopat(listOf(6, 5, 1, 1, 1))
        assertEquals(0, yk1.pisteet())

        var yk6 = Kutoset()
        yk6.syötäNopat(listOf(6, 6, 6, 6, 6))
        assertEquals(12, yk6.pisteet())
    }

    @Test
    fun testHuonoinYläkerta() {
        var yk = Yläkerta(Sarake._1)
        assertEquals(0, yk.pisteet())

        for (i in 1..6) yk.syötäNopat(i, listOf(i + 1, i + 1, i + 1, i + 1, i + 1))
        val huonoinTulos = (1..6).fold(0) { a, b -> a + (-3 * b) }
        assertEquals(huonoinTulos, yk.pisteet())

    }

    @Test
    fun testParasYläkerta() {
        var yk = Yläkerta(Sarake._1)
        assertEquals(0, yk.pisteet())

        for (i in 1..6) yk.syötäNopat(i, listOf(i, i, i, i, i))
        val parasTulos = (1..6).fold(50) { a, b -> a + (2 * b) }
        assertEquals(parasTulos, yk.pisteet())

    }

    @Test
    fun test50Yläkerta() {
        var yk = Yläkerta(Sarake._1)
        assertEquals(0, yk.pisteet())

        for (i in 1..6) yk.syötäNopat(i, listOf(i, i, i, i + 1, i + 1))
        assertEquals(50, yk.pisteet())

    }

    @Test
    fun testAlakerta() {
        val tulokset = Alakerta(Sarake._1)
        assertEquals(0, tulokset.pisteet())

        tulokset.lisääTulos(Pari(), listOf(1, 6, 6, 3, 6))
        assertEquals(12, tulokset.pisteet())

        tulokset.lisääTulos(Jatsi(), listOf(6, 6, 6, 6, 6))
        assertEquals(92, tulokset.pisteet())

    }

    @Test
    fun testLappu() {
        val lappu = Lappu()
        assertEquals(0, lappu.pisteet())

        lappu.lisääTulos(Tulos(Sarake._1, Pari(), listOf(1, 6, 6, 3, 6), 1))
        assertEquals(12, lappu.pisteet())

        lappu.lisääTulos(Tulos(Sarake._1, Jatsi(), listOf(6, 6, 6, 6, 6), 1))
        assertEquals(92, lappu.pisteet())

        lappu.lisääTulos(Tulos(Sarake._2, Pari(), listOf(1, 6, 6, 3, 6), 1))
        assertEquals(104, lappu.pisteet())

        lappu.lisääTulos(Tulos(Sarake._3, Pari(), listOf(1, 6, 6, 3, 6), 1))
        assertEquals(116, lappu.pisteet())

        lappu.lisääTulos(Tulos(Sarake._1, Ykköset(), listOf(1, 1, 1, 1, 1), 1))
        assertEquals(116, lappu.pisteet())

        lappu.lisääTulos(Tulos(Sarake._1, Kakkoset(), listOf(2, 2, 2, 1, 1), 1))
        lappu.lisääTulos(Tulos(Sarake._1, Kolmoset(), listOf(3, 3, 1, 3, 2), 1))
        lappu.lisääTulos(Tulos(Sarake._1, Neloset(), listOf(1, 1, 4, 4, 4), 1))
        lappu.lisääTulos(Tulos(Sarake._1, Vitoset(), listOf(1, 5, 5, 2, 5), 1))
        lappu.lisääTulos(Tulos(Sarake._1, Kutoset(), listOf(6, 6, 6, 2, 2), 1))

        assertEquals(168, lappu.pisteet())

    }

    @Test
    fun testNopat() {
        val nopat = Nopat()
        assertEquals("---, ---, ---, ---, ---", nopat.tulostaNopat())

        nopat.heitä()
        val regEx = """\d, \d, \d, \d, \d""".toRegex()
        assertTrue(regEx.matches(nopat.tulostaNopat()))

    }
}
