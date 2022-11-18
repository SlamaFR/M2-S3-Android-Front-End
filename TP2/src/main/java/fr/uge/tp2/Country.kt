package fr.uge.tp2

import androidx.compose.runtime.Composable
import fr.uge.tp2.flag.France.FranceFlag
import fr.uge.tp2.flag.Japan.JapanFlag
import fr.uge.tp2.flag.Poland.PolandFlag
import fr.uge.tp2.flag.SouthAfrica.SouthAfricaFlag
import java.util.TimeZone

data class Country(
    val name: Int,
    val resource: Int,
    val timeZone: TimeZone,
    val coordinates: Pair<Double, Double>,
    val flag: @Composable () -> Unit,
    val areaFact: AreaFact,
    val populationFact: PopulationFact,
    val densityFact: DensityFact,
    val gdpFact: GdpFact,
)

object Countries {
    val FRANCE = Country(
        name = R.string.country_france,
        resource = R.drawable.france,
        timeZone = TimeZone.getTimeZone("Europe/Paris"),
        coordinates = Pair(46.227638, 2.213749),
        flag = { FranceFlag() },
        areaFact = AreaFact(640_679f, Rank(42, 195)),
        populationFact = PopulationFact(67_939_000f, Rank(20, 195)),
        densityFact = DensityFact(117f, Rank(100, 195)),
        gdpFact = GdpFact(45_028f, Rank(23, 195)),
    )

    val JAPAN = Country(
        name = R.string.country_japan,
        resource = R.drawable.japan,
        timeZone = TimeZone.getTimeZone("Asia/Tokyo"),
        coordinates = Pair(36.204824, 138.252924),
        flag = { JapanFlag() },
        areaFact = AreaFact(377_976f, Rank(65, 195)),
        populationFact = PopulationFact(125_927_902f, Rank(11, 195)),
        densityFact = DensityFact(332f, Rank(12, 195)),
        gdpFact = GdpFact(40_704f, Rank(26, 195)),
    )

    val POLAND = Country(
        name = R.string.country_poland,
        resource = R.drawable.poland,
        timeZone = TimeZone.getTimeZone("Europe/Warsaw"),
        coordinates = Pair(51.919438, 19.145136),
        flag = { PolandFlag() },
        areaFact = AreaFact(312_696f, Rank(69, 195)),
        populationFact = PopulationFact(37_987_000f, Rank(38, 195)),
        densityFact = DensityFact(123f, Rank(98, 195)),
        gdpFact = GdpFact(15_431f, Rank(59, 195)),
    )

    val SOUTH_AFRICA = Country(
        name = R.string.country_south_africa,
        resource = R.drawable.south_africa,
        timeZone = TimeZone.getTimeZone("Africa/Johannesburg"),
        coordinates = Pair(-30.559482, 22.937506),
        flag = { SouthAfricaFlag() },
        areaFact = AreaFact(1_221_037f, Rank(24, 195)),
        populationFact = PopulationFact(60_604_992f, Rank(24, 195)),
        densityFact = DensityFact(49f, Rank(169, 195)),
        gdpFact = GdpFact(6_377f, Rank(91, 195)),
    )
}

val countries = listOf(
    Countries.FRANCE,
    Countries.JAPAN,
    Countries.POLAND,
    Countries.SOUTH_AFRICA,
)
