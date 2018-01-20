package com.raghu.moviereminder.pojos;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.text.TextUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to hold all theatre names
 * Created by Raghu on 0004 04/10/2016.
 */

public final class VenueNames {
    private final Map<String, Venue> venueMap = new HashMap<>(150);
    private static VenueNames INSTANCE;

    private VenueNames() {
        venueMap.put("ACHI", new Venue("ACHI", "Asian Sha: Chintal"));
        venueMap.put("ACPM", new Venue("ACPM", "Asian Lakshmikala Cinepride: Moosapet"));
        venueMap.put("ADHY", new Venue("ADHY", "Carnival: Ameerpet"));
        venueMap.put("AGHM", new Venue("AGHM", "Asian GPR Multiplex: Kukatpally"));
        venueMap.put("AHMH", new Venue("AHMH", "Asian CineSquare Multiplex: Uppal"));
        venueMap.put("AMCA", new Venue("AMCA", "Asian M Cube Mall: Attapur"));
        venueMap.put("ANTT", new Venue("ANTT", "Anjali Theatre: Secunderabad"));
        venueMap.put("ARJU", new Venue("ARJU", "Arjun 70MM: Kukatpally"));
        venueMap.put("ARMH", new Venue("ARMH", "Asian Radhika Multiplex: ECIL"));
        venueMap.put("ASGN", new Venue("ASGN", "Asian Ganga: Dilsukhnagar"));
        venueMap.put("ASHT", new Venue("ASHT", "Asian Shahensha: Chintal"));
        venueMap.put("ASSH", new Venue("ASSH", "Asian Shiva: Dilsukhnagar"));
        venueMap.put("ATMD", new Venue("ATMD", "Amba Theatre: Mehdipatnam"));
        venueMap.put("BILS", new Venue("BILS", "Carnival: Leonia, Shameerpet"));
        venueMap.put("BJNG", new Venue("BJNG", "Bhujanga 70MM: Jeedimetla"));
        venueMap.put("BKMV", new Venue("BKMV", "BVK Multiplex Vijayalakshmi: LB Nagar"));
        venueMap.put("CMMA", new Venue("CMMA", "Cinepolis: Mantra Mall, Attapur"));
        venueMap.put("CNTW", new Venue("CNTW", "Cine Town: Miyapur"));
        venueMap.put("CPCL", new Venue("CPCL", "Cinepolis: CCPL Mall, Hyderabad"));
        venueMap.put("CPHY", new Venue("CPHY", "Cineplanet Multiplex: Kompally"));
        venueMap.put("CPMH", new Venue("CPMH", "Cinepolis: Manjeera Mall, Kukatpally"));
        venueMap.put("CXCB", new Venue("CXCB", "PVR: Inorbit, Cyberabad"));
        venueMap.put("CXHY", new Venue("CXHY", "PVR: RK Cineplex, Hyderabad"));
        venueMap.put("GCTC", new Venue("GCTC", "Galaxy Cinema: Toli Chowki"));
        venueMap.put("GLRY", new Venue("GLRY", "Glory Cinema: Mallepally"));
        venueMap.put("GNHB", new Venue("GNHB", "Ganesh 70mm: Shamshabad"));
        venueMap.put("GOKU", new Venue("GOKU", "Gokul 70MM: Erragadda"));
        venueMap.put("HMHD", new Venue("HMHD", "BR Hitech 70mm 4K Silver Scrn Projection: Madhapur"));
        venueMap.put("IKGH", new Venue("IKGH", "Indra: Karmanghat"));
        venueMap.put("INHY", new Venue("INHY", "INOX: GVK One, Banjara Hills"));
        venueMap.put("INMH", new Venue("INMH", "INOX: Maheshwari Parmeshwari Mall, Kachiguda"));
        venueMap.put("JDMB", new Venue("JDMB", "Jagadamba Cinema: Ghatkesar"));
        venueMap.put("JOTI", new Venue("JOTI", "Jyothi Cinema: IDA Bolaram"));
        venueMap.put("JYHY", new Venue("JYHY", "Jyothi Theatre: RC Puram"));
        venueMap.put("KCNK", new Venue("KCNK", "Krishna Cinema: Nagaram (Dammaiguda)"));
        venueMap.put("KDNH", new Venue("KDNH", "Konark: Dilsukhnagar"));
        venueMap.put("KMMH", new Venue("KMMH", "Kalyani Movie Max: Bowenpally"));
        venueMap.put("LAKS", new Venue("LAKS", "Lakshmi Theatre: Shamshabad"));
        venueMap.put("LKMT", new Venue("LKMT", "Lakshmi Kala Mandir: Alwal"));
        venueMap.put("MAHM", new Venue("MAHM", "Mallikarjuna 70mm A/C DTS: Kukatpally"));
        venueMap.put("MCBH", new Venue("MCBH", "Metro Cinema: Bahadurpura"));
        venueMap.put("MCSS", new Venue("MCSS", "Miraj Cinemas: Shalini Shivani, Hyderabad"));
        venueMap.put("MNJU", new Venue("MNJU", "Manju Cinema: Kalasiguda"));
        venueMap.put("MUAA", new Venue("MUAA", "Mukta A2 Cinemas: Abids"));
        venueMap.put("NKGH", new Venue("NKGH", "Nagendra: Karmanghat"));
        venueMap.put("NTAH", new Venue("NTAH", "Nartaki Theatre: Alwal"));
        venueMap.put("PACH", new Venue("PACH", "Padmavati Cinema: Kachiguda"));
        venueMap.put("PRAS", new Venue("PRAS", "Prashant Cinema: Secunderabad"));
        venueMap.put("PRCS", new Venue("PRCS", "Prashant Cinema: Secunderabad"));
        venueMap.put("PRHN", new Venue("PRHN", "Prasads: Hyderabad"));
        venueMap.put("PRHY", new Venue("PRHY", "Prasads: Large Screen"));
        venueMap.put("PTHM", new Venue("PTHM", "Priya Theatre: Malleypally"));
        venueMap.put("PTTH", new Venue("PTTH", "Alankar (Pratap Theatre): Langer House"));
        venueMap.put("PVHY", new Venue("PVHY", "PVR: Panjagutta, Hyderabad"));
        venueMap.put("PVSF", new Venue("PVSF", "PVR Forum Sujana Mall: Kukatpally, Hyderabad"));
        venueMap.put("RADS", new Venue("RADS", "Ramakrishna 70MM - Indra Cinemas: Abids"));
        venueMap.put("RAJY", new Venue("RAJY", "Rajyalakshmi 70mm: Uppal"));
        venueMap.put("RJDD", new Venue("RJDD", "Rajdhani Deluxe Theatre: Dilsukhnagar"));
        venueMap.put("RJYL", new Venue("RJYL", "Rajyalakshmi 70mm: Uppal"));
        venueMap.put("RKCP", new Venue("RKCP", "Rukmini Cinema: Patancheru"));
        venueMap.put("RSMM", new Venue("RSMM", "Raghavendra 70mm: Malkajgiri"));
        venueMap.put("RTHY", new Venue("RTHY", "Ranga Theatre: Jeedimetla"));
        venueMap.put("SAHD", new Venue("SAHD", "Satyam Theatre: Ameerpet"));
        venueMap.put("SAPN", new Venue("SAPN", "Sapna Cinemas: Abids"));
        venueMap.put("SASC", new Venue("SASC", "Srilatha and Sridevi Complex: Chanda Nagar"));
        venueMap.put("SCEH", new Venue("SCEH", "Sandhya 70mm: RTC X Roads"));
        venueMap.put("SCHC", new Venue("SCHC", "Sridevi Cinema: Chilakalguda"));
        venueMap.put("SCVM", new Venue("SCVM", "Sushma Cinema: Vanasthalipuram"));
        venueMap.put("SEIN", new Venue("SEIN", "Sensation Insomnia: Khairatabad"));
        venueMap.put("SELE", new Venue("SELE", "Select Talkies: Bolaram"));
        venueMap.put("SENS", new Venue("SENS", "Sensation Sunshine: Khairatabad"));
        venueMap.put("SHHY", new Venue("SHHY", "Shanti Theatre: Narayanaguda"));
        venueMap.put("SHRD", new Venue("SHRD", "Sharada 70mm: Kapra"));
        venueMap.put("SKTA", new Venue("SKTA", "Sri Krishna Theatre: Aliabad (Shameerpet)"));
        venueMap.put("SLDH", new Venue("SLDH", "Sri Lakshmi Deluxe: Karmanghat"));
        venueMap.put("SLSD", new Venue("SLSD", "Srilatha and Sridevi Complex: Chanda Nagar"));
        venueMap.put("SMPR", new Venue("SMPR", "Sampoorna 70mm: Vanasthalipuram"));
        venueMap.put("SMTR", new Venue("SMTR", "Sri Mayuri Theatre: RTC X Roads"));
        venueMap.put("SMYU", new Venue("SMYU", "Sri Mayuri Theatre: RTC X Roads"));
        venueMap.put("SNDY", new Venue("SNDY", "Sandhya 35mm: RTC X Road"));
        venueMap.put("SNTH", new Venue("SNTH", "Srinivasa Talkies: Uppal"));
        venueMap.put("SPCB", new Venue("SPCB", "Super Cinema: Balapur"));
        venueMap.put("SPHT", new Venue("SPHT", "Sri Prema: Tukkuguda"));
        venueMap.put("SRCA", new Venue("SRCA", "Sree Ramana 70MM: Amberpet"));
        venueMap.put("SRCH", new Venue("SRCH", "Sree Ramana Digital 35mm: Amberpet"));
        venueMap.put("SRCM", new Venue("SRCM", "Sai Ranga: Miyapur"));
        venueMap.put("SREE", new Venue("SREE", "Sree Rama Theatre: Bahadurpura"));
        venueMap.put("SRKR", new Venue("SRKR", "Sri Krishna 70MM: Uppal"));
        venueMap.put("SRMO", new Venue("SRMO", "Sree Ramulu 70mm: Moosapet"));
        venueMap.put("SSET", new Venue("SSET", "Santosh Cinemas: Abids"));
        venueMap.put("SSHD", new Venue("SSHD", "SVC Sangeetha Theatre: RC Puram"));
        venueMap.put("SSPH", new Venue("SSPH", "Sri Sai Puja: Suraram"));
        venueMap.put("SSRM", new Venue("SSRM", "Sri Sai Ram Theatre: Malkajgiri"));
        venueMap.put("STIT", new Venue("STIT", "Santosh Theatre: Ibrahimpatnam"));
        venueMap.put("SUDA", new Venue("SUDA", "Sudarshan 35mm: Rtc X Roads"));
        venueMap.put("SUHC", new Venue("SUHC", "Suresh Theatre: Sithaphalmandi"));
        venueMap.put("SVCE", new Venue("SVCE", "SVC Cinemas Eeshwar: Attapur"));
        venueMap.put("SVCH", new Venue("SVCH", "SVC Mahalakshmi Screen 2: Kothapet"));
        venueMap.put("SVCM", new Venue("SVCM", "SVC Mahalakshmi Screen 1: Kothapet"));
        venueMap.put("SVLT", new Venue("SVLT", "Sri Vijayalakshmi Talkies: Maheshwaram"));
        venueMap.put("SVPV", new Venue("SVPV", "Shiva Parvathi Cinema: Kukatpally"));
        venueMap.put("SVSA", new Venue("SVSA", "Sri Venkata Sai 70MM: Keesara"));
        venueMap.put("SVST", new Venue("SVST", "Siva Sakthi Theatre: Kapra"));
        venueMap.put("TLUR", new Venue("TLUR", "Talluri Theatres: Kushaiguda"));
        venueMap.put("TRHY", new Venue("TRHY", "Tarakarama Cineplex: Kachiguda"));
        venueMap.put("TSCH", new Venue("TSCH", "Shama Cinema: Hyderabad"));
        venueMap.put("TTKH", new Venue("TTKH", "Talluri Theatres: Kushaiguda"));
        venueMap.put("TVCE", new Venue("TVCE", "Tivoli Cinema: Extreem"));
        venueMap.put("TVHY", new Venue("TVHY", "Tivoli Cinema: Hyderabad"));
        venueMap.put("VAJA", new Venue("VAJA", "Vyjayanthi Cinema: Nacharam"));
        venueMap.put("VECH", new Venue("VECH", "Venkataramana Cinema: Kachiguda"));
        venueMap.put("VENH", new Venue("VENH", "Venkatadri Cinema 70MM: Dilsukhnagar"));
        venueMap.put("VKCN", new Venue("VKCN", "SSV Cine Park: Patancheru"));
        venueMap.put("VLHB", new Venue("VLHB", "Vimal 70mm: Balanagar"));
        venueMap.put("VTVM", new Venue("VTVM", "Vishnu Cinemas A/C DTS: Vanasthalipuram"));
        venueMap.put("ARTH", new Venue("ARTH", "Aradhana Theatre: Hyderabad"));
        venueMap.put("ASJY", new Venue("ASJY", "Asian Jyothi: RC Puram"));
        venueMap.put("ASRK", new Venue("ASRK", "Asian Swapna 35mm: Katedan"));
        venueMap.put("ASRS", new Venue("ASRS", "Asian Swapna 70mm: Katedan"));
        venueMap.put("HMHD", new Venue("HMHD", "BR Hitech 70mm: Madhapur"));
        venueMap.put("PMKC", new Venue("PMKC", "Padmavati Cinema: Kachiguda"));
        venueMap.put("SRNU", new Venue("SRNU", "Srinivasa Talkies: Uppal"));
        venueMap.put("SUDA", new Venue("SUDA", "Sudarshan 35mm 4k & Dolby Atmos: RTC X Roads"));
        venueMap.put("SVPK", new Venue("SVPK", "Shiva Parvathi Cinema: Kukatpally"));
        venueMap.put("TGMG", new Venue("TGMG", "Megha Theatre: Dilsukhnagar"));
        venueMap.put("VTRB", new Venue("VTRB", "Vijetha 70MM: Borabanda"));
        venueMap.put("KTKT", new Venue("KTKT", "Kumar Theatre: Kachiguda"));
    }

    @NonNull
    public static String getTheatreName(@NonNull String code) {
        String value = getInstance().venueMap.get(code).name;
        if (TextUtils.isEmpty(value)) {
            return code;
        }
        return value;
    }

    @NonNull
    public static Collection<Venue> getTheatreNameSet() {
        return getInstance().venueMap.values();
    }

    @Nullable
    public static String getTheatreCode(@NonNull@Size(min = 4) String theatre) {
        if(getInstance().venueMap.containsKey(theatre)) {
            return theatre;
        }

        for(Map.Entry<String, Venue> entry : getInstance().venueMap.entrySet()) {
            if(theatre.equals(entry.getValue().name)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static Map<String, String> getTheatreMap(List<String> theatreCodes) {
        Map<String, String> returnVal = new HashMap<>(theatreCodes.size(), 1.75f);

        for(String theatreCode : theatreCodes) {
            returnVal.put(theatreCode, getTheatreName(theatreCode));
        }

        return returnVal;
    }

    @NonNull
    private static VenueNames getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new VenueNames();
        }
        return INSTANCE;
    }
}
