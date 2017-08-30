package com.raghu.moviereminder;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**Class to hold all theatre names
 * Created by Raghu on 0004 04/10/2016.
 */

final class VenueNames {
    private final Map<String, String> venueMap = new HashMap<>(150);
    private static VenueNames INSTANCE;
    private VenueNames () {
        venueMap.put("GNHB", "Ganesh 70mm: Shamshabad");
        venueMap.put("SHHY", "Shanti Theatre: Narayanaguda");
        venueMap.put("TSCH", "Shama Cinema: Hyderabad");
        venueMap.put("VAJA", "Vyjayanthi Cinema: Nacharam");
        venueMap.put("SVLT", "Sri Vijayalakshmi Talkies: Maheshwaram");
        venueMap.put("SPHT", "Sri Prema: Tukkuguda");
        venueMap.put("ATMD", "Amba Theatre: Mehdipatnam");
        venueMap.put("SNDY", "Sandhya 35mm: RTC X Road");
        venueMap.put("VLHB", "Vimal 70mm: Balanagar");
        venueMap.put("ASSH", "Asian Shiva: Dilsukhnagar");
        venueMap.put("SREE", "Sree Rama Theatre: Bahadurpura");
        venueMap.put("AHMH", "Asian CineSquare Multiplex: Uppal");
        venueMap.put("BJNG", "Bhujanga 70MM: Jeedimetla");
        venueMap.put("SRKR", "Sri Krishna 70MM: Uppal");
        venueMap.put("CPHY", "Cineplanet Multiplex: Kompally");
        venueMap.put("SCVM", "Sushma Cinema: Vanasthalipuram");
        venueMap.put("ARJU", "Arjun 70MM: Kukatpally");
        venueMap.put("SRCH", "Sree Ramana Digital 35mm: Amberpet");
        venueMap.put("SVCE", "SVC Cinemas Eeshwar: Attapur");
        venueMap.put("SVST", "Siva Sakthi Theatre: Kapra");
        venueMap.put("TRHY", "Tarakarama Cineplex: Kachiguda");
        venueMap.put("SNTH", "Srinivasa Talkies: Uppal");
        venueMap.put("SSET", "Santosh Cinemas: Abids");
        venueMap.put("SVCM", "SVC Mahalakshmi Screen 1: Kothapet");
        venueMap.put("PTHM", "Priya Theatre: Malleypally");
        venueMap.put("SRCM", "Sai Ranga: Miyapur");
        venueMap.put("SVCH", "SVC Mahalakshmi Screen 2: Kothapet");
        venueMap.put("CNTW", "Cine Town: Miyapur");
        venueMap.put("PACH", "Padmavati Cinema: Kachiguda");
        venueMap.put("TVHY", "Tivoli Cinema: Hyderabad");
        venueMap.put("KCNK", "Krishna Cinema: Nagaram (Dammaiguda)");
        venueMap.put("SASC", "Srilatha and Sridevi Complex: Chanda Nagar");
        venueMap.put("RKCP", "Rukmini Cinema: Patancheru");
        venueMap.put("TVCE", "Tivoli Cinema: Extreem");
        venueMap.put("LKMT", "Lakshmi Kala Mandir: Alwal");
        venueMap.put("NTAH", "Nartaki Theatre: Alwal");
        venueMap.put("MCSS", "Miraj Cinemas: Shalini Shivani, Hyderabad");
        venueMap.put("JOTI", "Jyothi Cinema: IDA Bolaram");
        venueMap.put("CXHY", "PVR: RK Cineplex, Hyderabad");
        venueMap.put("SPCB", "Super Cinema: Balapur");
        venueMap.put("PTTH", "Pratap Theatre: Langer House");
        venueMap.put("KMMH", "Kalyani Movie Max: Bowenpally");
        venueMap.put("SHRD", "Sharada 70mm: Kapra");
        venueMap.put("SRMO", "Sree Ramulu 70mm: Moosapet");
        venueMap.put("GOKU", "Gokul 70MM: Erragadda");
        venueMap.put("ADHY", "BIG Cinemas: Ameerpet");
        venueMap.put("STIT", "Santosh Theatre: Ibrahimpatnam");
        venueMap.put("GCTC", "Galaxy Cinema: Toli Chowki");
        venueMap.put("SELE", "Select Talkies: Bolaram");
        venueMap.put("JDMB", "Jagadamba Cinema: Ghatkesar");
        venueMap.put("SCHC", "Sridevi Cinema: Chilakalguda");
        venueMap.put("ANTT", "Anjali Theatre: Secunderabad");
        venueMap.put("MCBH", "Metro Cinema: Bahadurpura");
        venueMap.put("NKGH", "Nagendra: Karmanghat");
        venueMap.put("SSHD", "SVC Sangeetha Theatre: RC Puram");
        venueMap.put("VECH", "Venkataramana Cinema: Kachiguda");
        venueMap.put("SMTR", "Sri Mayuri Theatre: RTC X Roads");
        venueMap.put("SSPH", "Sri Sai Puja: Suraram");
        venueMap.put("ACPM", "Asian Lakshmikala Cinepride: Moosapet");
        venueMap.put("VENH", "Venkatadri Cinema 70MM: Dilsukhnagar");
        venueMap.put("JYHY", "Jyothi Theatre: RC Puram");
        venueMap.put("ASGN", "Asian Ganga: Dilsukhnagar");
        venueMap.put("MNJU", "Manju Cinema: Kalasiguda");
        venueMap.put("CPCL", "Cinepolis: CCPL Mall Malkajgiri, Hyderabad");
        venueMap.put("CXCB", "PVR: Inorbit, Cyberabad");
        venueMap.put("SVPV", "Shiva Parvathi Cinema: Kukatpally");
        venueMap.put("PRAS", "Prashant Cinema: Secunderabad");
        venueMap.put("ARMH", "Asian Radhika Multiplex: ECIL");
        venueMap.put("AMCA", "Asian M Cube Mall: Attapur");
        venueMap.put("RAJY", "Rajyalakshmi 70mm: Uppal");
        venueMap.put("ASHT", "Asian Shahensha: Chintal");
        venueMap.put("IKGH", "Indra: Karmanghat");
        venueMap.put("INMH", "INOX: Maheshwari Parmeshwari Mall, Kachiguda");
        venueMap.put("BKMV", "BVK Multiplex Vijayalakshmi: LB Nagar");
        venueMap.put("PRHN", "Prasads: Hyderabad");
        venueMap.put("SENS", "Sensation Sunshine: Khairatabad");
        venueMap.put("PVHY", "PVR: Panjagutta, Hyderabad");
        venueMap.put("AGHM", "Asian GPR Multiplex: Kukatpally");
        venueMap.put("PRHY", "Prasads: Large Screen");
        venueMap.put("SLDH", "Sri Lakshmi Deluxe: Karmanghat");
        venueMap.put("ACHI", "Asian Sha: Chintal");
        venueMap.put("SSRM", "Sri Sai Ram Theatre: Malkajgiri");
        venueMap.put("LAKS", "Lakshmi Theatre: Shamshabad");
        venueMap.put("VKCN", "SSV Cine Park: Patancheru");
        venueMap.put("GLRY", "Glory Cinema: Mallepally");
        venueMap.put("SEIN", "Sensation Insomnia: Khairatabad");
        venueMap.put("INHY", "INOX: GVK One, Banjara Hills");
        venueMap.put("SCEH", "Sandhya 70mm: RTC X Roads");
        venueMap.put("SVSA", "Sri Venkata Sai 70MM: Keesara");
        venueMap.put("CPMH", "Cinepolis: Manjeera Mall Kukatpally, Hyderabad");
        venueMap.put("RADS", "Ramakrishna 70MM - Indra Cinemas: Abids");
        venueMap.put("SRCA", "Sree Ramana 70MM: Amberpet");
        venueMap.put("VTVM", "Vishnu Cinemas A/C DTS: Vanasthalipuram");
        venueMap.put("HMHD", "BR Hitech 70mm 4K Silver Scrn Projection: Madhapur");
        venueMap.put("SKTA", "Sri Krishna Theatre: Aliabad (Shameerpet)");
        venueMap.put("SAPN", "Sapna Cinemas: Abids");
        venueMap.put("SAHD", "Satyam Theatre: Ameerpet");
        venueMap.put("MUAA", "Mukta A2 Cinemas: Abids");
        venueMap.put("KDNH", "Konark: Dilsukhnagar");
        venueMap.put("RTHY", "Ranga Theatre: Jeedimetla");
        venueMap.put("SMPR", "Sampoorna 70mm: Vanasthalipuram");
        venueMap.put("TTKH", "Talluri Theatres: Kushaiguda");
        venueMap.put("SUHC", "Suresh Theatre: Sithaphalmandi");
        venueMap.put("PVSF", "PVR Sujana Forum Mall: Kukatpally, Hyderabad");
        venueMap.put("SUDA", "Sudarshan 35mm: Rtc X Roads");
    }

    static String getTheatreName(String code) {

        if(INSTANCE == null) {
            INSTANCE = new VenueNames();
        }

        String value = INSTANCE.venueMap.get(code);
        if(TextUtils.isEmpty(value)) {
            value = code;
        }
        return value;
    }
}
