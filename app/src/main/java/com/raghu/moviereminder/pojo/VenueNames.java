package com.raghu.moviereminder.pojo;

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
    private final Map<String, String> venueMap = new HashMap<>(150);
    private static VenueNames INSTANCE;

    private VenueNames() {
        venueMap.put("ACHI", "Asian Sha: Chintal");
        venueMap.put("ACPM", "Asian Lakshmikala Cinepride: Moosapet");
        venueMap.put("ADHY", "Carnival: Ameerpet");
        venueMap.put("AGHM", "Asian GPR Multiplex: Kukatpally");
        venueMap.put("AHMH", "Asian CineSquare Multiplex: Uppal");
        venueMap.put("AMCA", "Asian M Cube Mall: Attapur");
        venueMap.put("ANTT", "Anjali Theatre: Secunderabad");
        venueMap.put("ARJU", "Arjun 70MM: Kukatpally");
        venueMap.put("ARMH", "Asian Radhika Multiplex: ECIL");
        venueMap.put("ASGN", "Asian Ganga: Dilsukhnagar");
        venueMap.put("ASHT", "Asian Shahensha: Chintal");
        venueMap.put("ASSH", "Asian Shiva: Dilsukhnagar");
        venueMap.put("ATMD", "Amba Theatre: Mehdipatnam");
        venueMap.put("BILS", "Carnival: Leonia, Shameerpet");
        venueMap.put("BJNG", "Bhujanga 70MM: Jeedimetla");
        venueMap.put("BKMV", "BVK Multiplex Vijayalakshmi: LB Nagar");
        venueMap.put("CMMA", "Cinepolis: Mantra Mall, Attapur");
        venueMap.put("CNTW", "Cine Town: Miyapur");
        venueMap.put("CPCL", "Cinepolis: CCPL Mall, Hyderabad");
        venueMap.put("CPHY", "Cineplanet Multiplex: Kompally");
        venueMap.put("CPMH", "Cinepolis: Manjeera Mall, Kukatpally");
        venueMap.put("CXCB", "PVR: Inorbit, Cyberabad");
        venueMap.put("CXHY", "PVR: RK Cineplex, Hyderabad");
        venueMap.put("GCTC", "Galaxy Cinema: Toli Chowki");
        venueMap.put("GLRY", "Glory Cinema: Mallepally");
        venueMap.put("GNHB", "Ganesh 70mm: Shamshabad");
        venueMap.put("GOKU", "Gokul 70MM: Erragadda");
        venueMap.put("HMHD", "BR Hitech 70mm 4K Silver Scrn Projection: Madhapur");
        venueMap.put("IKGH", "Indra: Karmanghat");
        venueMap.put("INHY", "INOX: GVK One, Banjara Hills");
        venueMap.put("INMH", "INOX: Maheshwari Parmeshwari Mall, Kachiguda");
        venueMap.put("JDMB", "Jagadamba Cinema: Ghatkesar");
        venueMap.put("JOTI", "Jyothi Cinema: IDA Bolaram");
        venueMap.put("JYHY", "Jyothi Theatre: RC Puram");
        venueMap.put("KCNK", "Krishna Cinema: Nagaram (Dammaiguda)");
        venueMap.put("KDNH", "Konark: Dilsukhnagar");
        venueMap.put("KMMH", "Kalyani Movie Max: Bowenpally");
        venueMap.put("LAKS", "Lakshmi Theatre: Shamshabad");
        venueMap.put("LKMT", "Lakshmi Kala Mandir: Alwal");
        venueMap.put("MAHM", "Mallikarjuna 70mm A/C DTS: Kukatpally");
        venueMap.put("MCBH", "Metro Cinema: Bahadurpura");
        venueMap.put("MCSS", "Miraj Cinemas: Shalini Shivani, Hyderabad");
        venueMap.put("MNJU", "Manju Cinema: Kalasiguda");
        venueMap.put("MUAA", "Mukta A2 Cinemas: Abids");
        venueMap.put("NKGH", "Nagendra: Karmanghat");
        venueMap.put("NTAH", "Nartaki Theatre: Alwal");
        venueMap.put("PACH", "Padmavati Cinema: Kachiguda");
        venueMap.put("PRAS", "Prashant Cinema: Secunderabad");
        venueMap.put("PRCS", "Prashant Cinema: Secunderabad");
        venueMap.put("PRHN", "Prasads: Hyderabad");
        venueMap.put("PRHY", "Prasads: Large Screen");
        venueMap.put("PTHM", "Priya Theatre: Malleypally");
        venueMap.put("PTTH", "Alankar (Pratap Theatre): Langer House");
        venueMap.put("PVHY", "PVR: Panjagutta, Hyderabad");
        venueMap.put("PVSF", "PVR Forum Sujana Mall: Kukatpally, Hyderabad");
        venueMap.put("RADS", "Ramakrishna 70MM - Indra Cinemas: Abids");
        venueMap.put("RAJY", "Rajyalakshmi 70mm: Uppal");
        venueMap.put("RJDD", "Rajdhani Deluxe Theatre: Dilsukhnagar");
        venueMap.put("RJYL", "Rajyalakshmi 70mm: Uppal");
        venueMap.put("RKCP", "Rukmini Cinema: Patancheru");
        venueMap.put("RSMM", "Raghavendra 70mm: Malkajgiri");
        venueMap.put("RTHY", "Ranga Theatre: Jeedimetla");
        venueMap.put("SAHD", "Satyam Theatre: Ameerpet");
        venueMap.put("SAPN", "Sapna Cinemas: Abids");
        venueMap.put("SASC", "Srilatha and Sridevi Complex: Chanda Nagar");
        venueMap.put("SCEH", "Sandhya 70mm: RTC X Roads");
        venueMap.put("SCHC", "Sridevi Cinema: Chilakalguda");
        venueMap.put("SCVM", "Sushma Cinema: Vanasthalipuram");
        venueMap.put("SEIN", "Sensation Insomnia: Khairatabad");
        venueMap.put("SELE", "Select Talkies: Bolaram");
        venueMap.put("SENS", "Sensation Sunshine: Khairatabad");
        venueMap.put("SHHY", "Shanti Theatre: Narayanaguda");
        venueMap.put("SHRD", "Sharada 70mm: Kapra");
        venueMap.put("SKTA", "Sri Krishna Theatre: Aliabad (Shameerpet)");
        venueMap.put("SLDH", "Sri Lakshmi Deluxe: Karmanghat");
        venueMap.put("SLSD", "Srilatha and Sridevi Complex: Chanda Nagar");
        venueMap.put("SMPR", "Sampoorna 70mm: Vanasthalipuram");
        venueMap.put("SMTR", "Sri Mayuri Theatre: RTC X Roads");
        venueMap.put("SMYU", "Sri Mayuri Theatre: RTC X Roads");
        venueMap.put("SNDY", "Sandhya 35mm: RTC X Road");
        venueMap.put("SNTH", "Srinivasa Talkies: Uppal");
        venueMap.put("SPCB", "Super Cinema: Balapur");
        venueMap.put("SPHT", "Sri Prema: Tukkuguda");
        venueMap.put("SRCA", "Sree Ramana 70MM: Amberpet");
        venueMap.put("SRCH", "Sree Ramana Digital 35mm: Amberpet");
        venueMap.put("SRCM", "Sai Ranga: Miyapur");
        venueMap.put("SREE", "Sree Rama Theatre: Bahadurpura");
        venueMap.put("SRKR", "Sri Krishna 70MM: Uppal");
        venueMap.put("SRMO", "Sree Ramulu 70mm: Moosapet");
        venueMap.put("SSET", "Santosh Cinemas: Abids");
        venueMap.put("SSHD", "SVC Sangeetha Theatre: RC Puram");
        venueMap.put("SSPH", "Sri Sai Puja: Suraram");
        venueMap.put("SSRM", "Sri Sai Ram Theatre: Malkajgiri");
        venueMap.put("STIT", "Santosh Theatre: Ibrahimpatnam");
        venueMap.put("SUDA", "Sudarshan 35mm: Rtc X Roads");
        venueMap.put("SUHC", "Suresh Theatre: Sithaphalmandi");
        venueMap.put("SVCE", "SVC Cinemas Eeshwar: Attapur");
        venueMap.put("SVCH", "SVC Mahalakshmi Screen 2: Kothapet");
        venueMap.put("SVCM", "SVC Mahalakshmi Screen 1: Kothapet");
        venueMap.put("SVLT", "Sri Vijayalakshmi Talkies: Maheshwaram");
        venueMap.put("SVPV", "Shiva Parvathi Cinema: Kukatpally");
        venueMap.put("SVSA", "Sri Venkata Sai 70MM: Keesara");
        venueMap.put("SVST", "Siva Sakthi Theatre: Kapra");
        venueMap.put("TLUR", "Talluri Theatres: Kushaiguda");
        venueMap.put("TRHY", "Tarakarama Cineplex: Kachiguda");
        venueMap.put("TSCH", "Shama Cinema: Hyderabad");
        venueMap.put("TTKH", "Talluri Theatres: Kushaiguda");
        venueMap.put("TVCE", "Tivoli Cinema: Extreem");
        venueMap.put("TVHY", "Tivoli Cinema: Hyderabad");
        venueMap.put("VAJA", "Vyjayanthi Cinema: Nacharam");
        venueMap.put("VECH", "Venkataramana Cinema: Kachiguda");
        venueMap.put("VENH", "Venkatadri Cinema 70MM: Dilsukhnagar");
        venueMap.put("VKCN", "SSV Cine Park: Patancheru");
        venueMap.put("VLHB", "Vimal 70mm: Balanagar");
        venueMap.put("VTVM", "Vishnu Cinemas A/C DTS: Vanasthalipuram");
        venueMap.put("ARTH", "Aradhana Theatre: Hyderabad");
        venueMap.put("ASJY", "Asian Jyothi: RC Puram");
        venueMap.put("ASRK", "Asian Swapna 35mm: Katedan");
        venueMap.put("ASRS", "Asian Swapna 70mm: Katedan");
        venueMap.put("HMHD", "BR Hitech 70mm: Madhapur");
        venueMap.put("PMKC", "Padmavati Cinema: Kachiguda");
        venueMap.put("SRNU", "Srinivasa Talkies: Uppal");
        venueMap.put("SUDA", "Sudarshan 35mm 4k & Dolby Atmos: RTC X Roads");
        venueMap.put("SVPK", "Shiva Parvathi Cinema: Kukatpally");
        venueMap.put("TGMG", "Megha Theatre: Dilsukhnagar");
        venueMap.put("VTRB", "Vijetha 70MM: Borabanda");
        venueMap.put("KTKT", "Kumar Theatre: Kachiguda");
    }

    @NonNull
    public static String getTheatreName(@NonNull String code) {
        String value = getInstance().venueMap.get(code);
        if (TextUtils.isEmpty(value)) {
            return code;
        }
        return value;
    }

    @NonNull
    public static Collection<String> getTheatreNameSet() {
        return getInstance().venueMap.values();
    }

    @Nullable
    public static String getTheatreCode(@NonNull@Size(min = 4) String theatre) {
        if(getInstance().venueMap.containsKey(theatre)) {
            return theatre;
        }

        for(Map.Entry<String, String> entry : getInstance().venueMap.entrySet()) {
            if(theatre.equals(entry.getValue())) {
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
