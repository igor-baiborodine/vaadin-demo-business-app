package com.kiroule.vaadin.bakeryapp.backend;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.vaadin.flow.component.html.Image;
import com.kiroule.vaadin.bakeryapp.ui.util.UIUtils;

public class DummyData {

    private static final Map<Long, Report> REPORTS = new HashMap<>();
    private static final Map<Long, Person> PERSONS = new HashMap<>();
    private static final Map<Long, Transaction> TRANSACTIONS = new HashMap<>();
    private static final Map<Long, BankAccount> BANK_ACCOUNTS = new HashMap<>();
    private static final Map<Long, Payment> PAYMENTS = new HashMap<>();
    private static final Map<Long, Item> ITEMS = new HashMap<>();
    private static final Map<Long, Order> ORDERS = new HashMap<>();
    private static final Map<Long, Invoice> INVOICES = new HashMap<>();

    private static final Random random = new Random(1);

    private static final String[] IBANS = new String[] {
            "AD12 0001 2030 2003 5910 0100", "AE07 0331 2345 6789 0123 456",
            "AL47 2121 1009 0000 0002 3569 8741", "AT61 1904 3002 3457 3201",
            "AZ21 NABZ 0000 0000 1370 1000 1944", "BA39 1290 0794 0102 8494",
            "BE68 5390 0754 7034", "BG80 BNBG 9661 1020 3456 78",
            "BH67 BMAG 0000 1299 1234 56",
            "BR18 0036 0305 0000 1000 9795 493C 1",
            "BY13 NBRB 3600 9000 0000 2Z00 AB00", "CH93 0076 2011 6238 5295 7",
            "CR05 0152 0200 1026 2840 66", "CY17 0020 0128 0000 0012 0052 7600",
            "CZ65 0800 0000 1920 0014 5399", "DE89 3704 0044 0532 0130 00",
            "DK50 0040 0440 1162 43", "DO28 BAGR 0000 0001 2124 5361 1324",
            "EE38 2200 2210 2014 5685", "ES91 2100 0418 4502 0005 1332",
            "FI21 1234 5600 0007 85", "FO62 6460 0001 6316 34",
            "FR14 2004 1010 0505 0001 3M02 606", "GB29 NWBK 6016 1331 9268 19",
            "GE29 NB00 0000 0101 9049 17", "GI75 NWBK 0000 0000 7099 453",
            "GL89 6471 0001 0002 06", "GR16 0110 1250 0000 0001 2300 695",
            "GT82 TRAJ 0102 0000 0012 1002 9690", "HR12 1001 0051 8630 0016 0",
            "HU42 1177 3016 1111 1018 0000 0000", "IE29 AIBK 9311 5212 3456 78",
            "IL62 0108 0000 0009 9999 999", "IQ98 NBIQ 8501 2345 6789 012",
            "IS14 0159 2600 7654 5510 7303 39",
            "IT60 X054 2811 1010 0000 0123 456",
            "JO94 CBJO 0010 0000 0000 0131 0003 02",
            "KW81 CBKU 0000 0000 0000 1234 5601 01", "KZ86 125K ZT50 0410 0100",
            "LB62 0999 0000 0001 0019 0122 9114",
            "LC55 HEMM 0001 0001 0012 0012 0002 3015",
            "LI21 0881 0000 2324 013A A", "LT12 1000 0111 0100 1000",
            "LU28 0019 4006 4475 0000", "LV80 BANK 0000 4351 9500 1",
            "MC58 1122 2000 0101 2345 6789 030",
            "MD24 AG00 0225 1000 1310 4168", "ME25 5050 0001 2345 6789 51",
            "MK07 2501 2000 0058 984", "MR13 0002 0001 0100 0012 3456 753",
            "MT84 MALT 0110 0001 2345 MTLC AST0 01S",
            "MU17 BOMM 0101 1010 3030 0200 000M UR", "NL91 ABNA 0417 1643 00",
            "NO93 8601 1117 947", "PK36 SCBL 0000 0011 2345 6702",
            "PL61 1090 1014 0000 0712 1981 2874",
            "PS92 PALS 0000 0000 0400 1234 5670 2",
            "PT50 0002 0123 1234 5678 9015 4",
            "QA58 DOHB 0000 1234 5678 90AB CDEF G",
            "RO49 AAAA 1B31 0075 9384 0000", "RS35 2600 0560 1001 6113 79",
            "SA03 8000 0000 6080 1016 7519",
            "SC18 SSCB 1101 0000 0000 0000 1497 USD",
            "SE45 5000 0000 0583 9825 7466", "SI56 2633 0001 2039 086",
            "SK31 1200 0000 1987 4263 7541",
            "SM86 U032 2509 8000 0000 0270 100",
            "ST68 0002 0001 0192 1942 1011 2",
            "SV62 CENR 0000 0000 0000 0070 0025",
            "TL38 0080 0123 4567 8910 157", "TN59 1000 6035 1835 9847 8831",
            "TR33 0006 1005 1978 6457 8413 26",
            "UA21 3996 2200 0002 6007 2335 6600 1",
            "VG96 VPVG 0000 0123 4567 8901", "XK05 1212 0123 4567 8906",
            "YY24 KIHB 1247 6423 1259 1594 7930 9152 68",
            "ZZ25 VLQT 3823 3223 3206 5880 1131 3776 421" };
    private static final String[] COMPANIES = new String[] {
            "Brewer Holding Century Training", "Broadcast Electric",
            "Chemical General Development", "Digital Agricultural Dynamics",
            "Demondu Semiconductors", "Cota Realisations",
            "European Photographic", "Financial Supplies",
            "Genetics Leasing Mechanical", "Henderson Publishing",
            "Innovative Cyberbank", "Prosaria Equipment Networks", "Kilobank",
            "McCray Mechanical Manufacturing", "Medical Telecommunications",
            "Navarro Horizon Gold Products", "Network Office Contractors",
            "Sharp Network Sciences", "Speciality Investments",
            "Tanner Progressive Healthcare", "Terrell Royal Vistas",
            "Esill Integration", "Aeon Transportation Atomic",
            "Fambu Development", "Atomic Broadcasting", "Boyer Scientific",
            "Buck Motors", "Canadian Speciality Group",
            "Cargo Leasing Instructional", "Clark Industrial Semiconductors",
            "Engineered Open Sports", "European Chemical",
            "Fletcher Applied Aeon Power", "Fulton Memory Brokerage",
            "Lang Engineering", "Logistics Globosoft", "Medical Equipment",
            "Office Tele-Dynamics", "Skagen Unlimited", "Sawyer Technics",
            "Sharpe Genetics", "Comend Inc", "Strong Wholesale Manufacturing",
            "Diadoo Training", "Villarreal Industrial", "Wade Frontiers",
            "Wilcox Group", "Cement Precast Products", "Vicejo",
            "Microdel Service", "Skanti International", "Surous Plastics",
            "Genteel Graphics", "Conner Hearing Aid Clinic",
            "Nora Brennan Casting", "Cumbre", "Grafika", "Time Index",
            "Twiveo Printing & Mailing", "Peix & Marchetti Architects",
            "Quiyo Products", "Hermell Products", "Porter Chemical",
            "Chevron Asphalt Plant", "Texas Petrochemical",
            "Ladoo Finishing Equipment", "Paradeo Financial", "Semimm",
            "Redeo Continential", "Merten Co", "Hydronix Packaging",
            "Lafayette Printing", "Kanti Enterprise", "Frank Tava", "Promark",
            "Artisan Woods", "Divalane Summer", "Roodel Color Graphics",
            "Color Graphx", "Fastrax Enterprise", "Classic Markets Corp",
            "Builders & Homeowners Supply", "Prosaria Print Shop",
            "Conill Performance", "Camicee Coatings", "Protocero Networks",
            "Go Dynamics", "General Plastex", "Garible Durable",
            "Antelium Life Sciences" };
    private static final String[] BANKS = new String[] { "Aegis Bank",
            "Kindred Credit Union", "Oculus Bank System", "Aspire Credit Union",
            "Meridian Bancorp", "Focus Trust", "Cloud Nine Financial Corp.",
            "Lifespark Banks", "Obelisk Corporation", "Principal Banks" };
    private static final String[] FIRST_NAMES = new String[] { "James", "Mary",
            "John", "Patricia", "Robert", "Jennifer", "Michael", "Linda",
            "William", "Elizabeth", "David", "Barbara", "Richard", "Susan",
            "Joseph", "Jessica", "Thomas", "Sarah", "Charles", "Margaret",
            "Christopher", "Karen", "Daniel", "Nancy", "Matthew", "Lisa",
            "Anthony", "Betty", "Donald", "Dorothy", "Mark", "Sandra", "Paul",
            "Ashley", "Steven", "Kimberly", "Andrew", "Donna", "Kenneth",
            "Emily", "George", "Carol", "Joshua", "Michelle", "Kevin", "Amanda",
            "Brian", "Melissa", "Edward", "Deborah", "Ronald", "Stephanie",
            "Timothy", "Rebecca", "Jason", "Laura", "Jeffrey", "Helen", "Ryan",
            "Sharon", "Jacob", "Cynthia", "Gary", "Kathleen", "Nicholas", "Amy",
            "Eric", "Shirley", "Stephen", "Angela", "Jonathan", "Anna", "Larry",
            "Ruth", "Justin", "Brenda", "Scott", "Pamela", "Brandon", "Nicole",
            "Frank", "Katherine", "Benjamin", "Samantha", "Gregory",
            "Christine", "Raymond", "Catherine", "Samuel", "Virginia",
            "Patrick", "Debra", "Alexander", "Rachel", "Jack", "Janet",
            "Dennis", "Emma", "Jerry", "Carolyn", "Tyler", "Maria", "Aaron",
            "Heather", "Henry", "Diane", "Jose", "Julie", "Douglas", "Joyce",
            "Peter", "Evelyn", "Adam", "Joan", "Nathan", "Victoria", "Zachary",
            "Kelly", "Walter", "Christina", "Kyle", "Lauren", "Harold",
            "Frances", "Carl", "Martha", "Jeremy", "Judith", "Gerald", "Cheryl",
            "Keith", "Megan", "Roger", "Andrea", "Arthur", "Olivia", "Terry",
            "Ann", "Lawrence", "Jean", "Sean", "Alice", "Christian",
            "Jacqueline", "Ethan", "Hannah", "Austin", "Doris", "Joe",
            "Kathryn", "Albert", "Gloria", "Jesse", "Teresa", "Willie", "Sara",
            "Billy", "Janice", "Bryan", "Marie", "Bruce", "Julia", "Noah",
            "Grace", "Jordan", "Judy", "Dylan", "Theresa", "Ralph", "Madison",
            "Roy", "Beverly", "Alan", "Denise", "Wayne", "Marilyn", "Eugene",
            "Amber", "Juan", "Danielle", "Gabriel", "Rose", "Louis", "Brittany",
            "Russell", "Diana", "Randy", "Abigail", "Vincent", "Natalie",
            "Philip", "Jane", "Logan", "Lori", "Bobby", "Alexis", "Harry",
            "Tiffany", "Johnny", "Kayla" };
    private static final String[] LAST_NAMES = new String[] { "Smith",
            "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller",
            "Wilson", "Moore", "Taylor", "Anderson", "Thomas", "Jackson",
            "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez",
            "Robinson", "Clark", "Rodriguez", "Lewis", "Lee", "Walker", "Hall",
            "Allen", "Young", "Hernandez", "King", "Wright", "Lopez", "Hill",
            "Scott", "Green", "Adams", "Baker", "Gonzalez", "Nelson", "Carter",
            "Mitchell", "Perez", "Roberts", "Turner", "Phillips", "Campbell",
            "Parker", "Evans", "Edwards", "Collins", "Stewart", "Sanchez",
            "Morris", "Rogers", "Reed", "Cook", "Morgan", "Bell", "Murphy",
            "Bailey", "Rivera", "Cooper", "Richardson", "Cox", "Howard", "Ward",
            "Torres", "Peterson", "Gray", "Ramirez", "James", "Watson",
            "Brooks", "Kelly", "Sanders", "Price", "Bennett", "Wood", "Barnes",
            "Ross", "Henderson", "Coleman", "Jenkins", "Perry", "Powell",
            "Long", "Patterson", "Hughes", "Flores", "Washington", "Butler",
            "Simmons", "Foster", "Gonzales", "Bryant ", "Alexander", "Russell",
            "Griffin ", "Diaz", "Hayes" };

    private static final ArrayList<Address> ADDRESSES = new ArrayList<>();

    private static Map<String, String> HEALTHCARE = new HashMap<>();
    private static Map<String, String> DENTAL = new HashMap<>();
    private static Map<String, String> CONSTRUCTION = new HashMap<>();

    private DummyData() {
    }

    static {
        long i = 0;

        /* === REPORTS === */

        for (i = 0; i < 40; i++) {
            REPORTS.put(i, new Report(i, getImageSource(), getCompany(),
                    getPastDate(30), getFutureDate(30), getPositiveAmount()));
        }

        /* === PERSONS ==== */

        for (i = 0; i < 100; i++) {
            String firstName = getFirstName();
            String lastName = getLastName();
            PERSONS.put(i, new Person(i, firstName, lastName, getRole(),
                    firstName.toLowerCase() + "." + lastName.toLowerCase()
                            + "@email.com",
                    random.nextBoolean(), random.nextInt(500000), getDate()));
        }

        /* === TRANSACTIONS ==== */

        for (i = 0; i < 40; i++) {
            TRANSACTIONS.put(i, new Transaction(i + getRandomInt(0, 9999),
                    getTransactionStatus(), getCompany(), getIBAN(),
                    getAmount(), random.nextBoolean(), getPastDate(90)));
        }

        /* === BANK ACCOUNTS ==== */

        int startingPoint = 1000;
        for (i = 0; i < 40; i++) {
            BANK_ACCOUNTS.put(i + startingPoint,
                    new BankAccount(i + startingPoint, getBank(), getIBAN(),
                            getCompany(), getRandomDouble(5000, 100000),
                            getDate()));
        }

        /* === PAYMENTS ==== */

        for (i = 0; i < 40; i++) {
            PAYMENTS.put(i,
                    new Payment(getPaymentStatus(), getCompany(), getIBAN(),
                            getBank(), getIBAN(), getPositiveAmount(),
                            getDate()));
        }

        /* === ITEMS === */

        HEALTHCARE.put("Claritin",
                "Claritin® allergy medicine gives adults and kids non-drowsy relief from indoor and outdoor allergy symptoms.");
        HEALTHCARE.put("Cepacol",
                "Cepacol is the brand trusted and recommended by doctors and pharmacists to relieve your sore throat pain.");
        HEALTHCARE.put("Delsym",
                "Silence your cough for a full 12 hours with Delsym® cough syrup. Our fast-acting cough syrup products work up to twice as long as other cough liquids.");
        HEALTHCARE.put("DayQuil",
                "The non-drowsy, coughing, aching, fever, sore throat, stuffy head, no sick days medicine.");
        HEALTHCARE.put("NyQuil",
                "The nighttime, sniffling, sneezing, coughing, aching, fever, best sleep with a cold medicine.");

        HEALTHCARE.put("Mucinex",
                "Whether you have bothersome chest congestion or chest congestion with a nagging cough, Mucinex® has a range of products with bi-layer tablets that last for 12 hours.");
        HEALTHCARE.put("Vicks VapoRub",
                "Vick® Vaporub makes a mother's touch a touch more comforting.");
        HEALTHCARE.put("Non Medicated Vicks VapoInhaler",
                "Vicks® VapoInhaler™ is a non-medicated nasal inhaler that provides you with refreshing Vicks Vapors.");
        HEALTHCARE.put("Systane", "Expect more from your eye drop.");
        HEALTHCARE.put("OPTI-FREE Puremoist",
                "OPTI-FREE® Puremoist® is your multipurpose contact lens solution for clean, comfortable contacts all day.");

        HEALTHCARE.put("Similasan Earache Relief",
                "Fast, effective earache pain relief. Similasan earache drops are naturally safe. Natural alternative to antibiotics. Effective treatment for earache. Soothe ear pain.");
        HEALTHCARE.put("Breathe Right",
                "Breathe Right nasal strips provide nasal congestion relief to help you stop snoring and sleep better.");
        HEALTHCARE.put("Biotène",
                "The #1 Dry Mouth brand trusted by dentists.");
        HEALTHCARE.put("Listerine",
                "The bold mouthwash that kills 99% of bad breath germs.");
        HEALTHCARE.put("Sensodyne",
                "The #1 dentist recommended sensitivity toothpaste, can help provide relief and long-lasting protection for sensitive teeth.");

        HEALTHCARE.put("Advil",
                "Advil® delivers relief that's fast with strength that lasts.");
        HEALTHCARE.put("Aleve",
                "Aleve helps you make more from each day, uninterrupted by minor aches and pains such as headaches, backache or minor arthritis pain.");
        HEALTHCARE.put("Excedrin Migraine",
                "Excedrin® Migraine is a doctor-recommended safe and effective OTC migraine treatment.");
        HEALTHCARE.put("Tylenol PM",
                "Experience relief from aches and pains, while getting a good night's rest, with TYLENOL® PM Extra Strength.");
        HEALTHCARE.put("Osteo Bi-Flex",
                "Supporting your joint health year after year, Osteo Bi-Flex offers joint supplements made to provide joint comfort, mobility and flexibility.");

        HEALTHCARE.forEach((name, desc) -> ITEMS.put(Long.valueOf(ITEMS.size()),
                new Item(Item.Category.HEALTHCARE, name, desc, getCompany(),
                        getRandomDouble(40, 500), getRandomInt(0, 200),
                        getRandomInt(0, 50000))));

        DENTAL.put("Mirror",
                "Dental mirrors are used by the dentist or dental auxiliary to view a mirror image of the teeth in locations of the mouth where visibility is difficult or impossible.");
        DENTAL.put("Sickle Probe",
                "Used in the dental armamentarium. A sharp point at the end of the explorer is used to enhance tactile sensation.");
        DENTAL.put("Mouth Prop",
                "A wedge-shaped implement used in dentistry for dentists working with children and other patients who have difficulty keeping their mouths open wide and steady during a procedure, or during procedures where the patient is sedated.");
        DENTAL.put("Anesthesia",
                "Used to numb a small specific area of the mouth.");
        DENTAL.put("Syringe",
                "A dental syringe is a used by dentists for the injection of an anesthetic.");
        DENTAL.put("Drill",
                "Hand-held, mechanical instrument used to perform a variety of common dental procedures, including removing decay, polishing fillings, and altering prostheses.");

        DENTAL.put("Laser",
                "Designed specifically for use in oral surgery or dentistry.");
        DENTAL.put("Torque Wrench",
                "Used to precisely apply a specific torque to a fastener bolt for fixation of an abutment, dentures or prostetics[1] on a dental implant.");
        DENTAL.put("Spoon Excavator", "Used to remove soft carious decay.");
        DENTAL.put("Half Hollenbach", "Used to test for overhangs or flash.");
        DENTAL.put("Hatcher",
                "Used to widen the entrace of the tooth cavity and slice away the thin carious enamel.");

        DENTAL.put("Chisel (Straight)",
                "Bevels the cavosurface margin and used in 3, 4 and 5 classifications of cavities on the maxillary.");
        DENTAL.put("Chisel (Wedelstaedt)",
                "Only used in the anterior for classes 3, 4 and 5 as well.");
        DENTAL.put("Chisel (Bin Angle)",
                "Held in a pen grasp and used for class 2 maxillary only.");
        DENTAL.put("Burnisher",
                "For polishing and contouring amalgam fillings and to polish composite fillings.");
        DENTAL.put("Plugger",
                "Used to achieve a well condensed filling by compressing the filling material into the cavity and applying pressure.");

        DENTAL.forEach((name, desc) -> ITEMS.put(Long.valueOf(ITEMS.size()),
                new Item(Item.Category.DENTAL, name, desc, getCompany(),
                        getRandomDouble(40, 500), getRandomInt(0, 200),
                        getRandomInt(0, 50000))));

        CONSTRUCTION.put("Chapter 8 Barriers",
                "Ideal for event management and pedestrian control.");
        CONSTRUCTION.put("Manhole Barrier",
                "This product is compliant with Chapter 8 street works.");
        CONSTRUCTION.put("Water/Sand Filled Barrier",
                "Fully stackable for ease of transport and storage. Can be water or sand filled for a more permanent requirement.");
        CONSTRUCTION.put("Road Plates",
                "A long lasting and lightweight alternative to steel trench covers.");
        CONSTRUCTION.put("Folding Workshop Crane",
                "Quick lifting with a dual action pump.");

        CONSTRUCTION.put("Universal Drum Stacker",
                "Stacker with choice of drum claw or drum clamp.");
        CONSTRUCTION.put("Single Gas Cylinder Trolley",
                "50kg capacity gas cylinder trolley.");
        CONSTRUCTION.put("5 Channel Cable Protector",
                "Heavy duty cable protector weighing 23kg.");
        CONSTRUCTION.put("Hazard Tape",
                "Non-adhesive polyethylene barrier tape for cordoning of road works, construction sites and other hazard areas.");
        CONSTRUCTION.put("Safety Helmet",
                "UV-resistant high density Polyethylene (HDPE) safety helmet.");

        CONSTRUCTION.put("Safety Vest",
                "100% polyester fabric with Velcro fastening.");
        CONSTRUCTION.put("Permanent Line Marking Paint",
                "Permanent road line marking paint. Suitable for use on concrete, tarmac and composites.");
        CONSTRUCTION.put("Metal Dump Truck",
                "2mm thick steel sheet with double folded top rim construction.");
        CONSTRUCTION.put("Work Platform",
                "Aluminium 'Hop-Up' with unique folding first step to provide extra working level.");
        CONSTRUCTION.put("Scaffold Systems",
                "All aluminium construction with timber platform 1300 x 460mm.");

        CONSTRUCTION
                .forEach((name, desc) -> ITEMS.put(Long.valueOf(ITEMS.size()),
                        new Item(Item.Category.CONSTRUCTION, name, desc,
                                getCompany(), getRandomDouble(40, 500),
                                getRandomInt(0, 200), getRandomInt(0, 50000))));

        /* === ORDERS & INVOICES === */

        for (i = 0; i < 40; i++) {
            Order order = new Order(i + getRandomInt(0, 9999), getOrderStatus(),
                    getOrderItems(), getCompany(), getDate());
            ORDERS.put(i, order);

            Invoice invoice = new Invoice(i + getRandomInt(0, 9999),
                    getInvoiceStatus(), order, getPastDate(30),
                    getFutureDate(30));
            INVOICES.put(i, invoice);
        }

        /* === ADDRESSES === */

        ADDRESSES.add(
                new Address("8970 Birchpond St.", "Asheboro", "NC", "27205"));
        ADDRESSES.add(
                new Address("8669 Wild Horse St.", "Mahwah", "NJ", "07430"));
        ADDRESSES.add(
                new Address("8580 County Road", "Suitland", "MD", "20746"));
        ADDRESSES.add(
                new Address("42 Vermont Road", "Uniontown", "PA", "15401"));
        ADDRESSES
                .add(new Address("73 Brickell Drive", "Easton", "PA", "18042"));
        ADDRESSES.add(new Address("65B Glen Creek St.", "Spartanburg", "SC",
                "29301"));
        ADDRESSES.add(
                new Address("9686 South Shirley Dr.", "Nashua", "NH", "03060"));
        ADDRESSES.add(
                new Address("8409 Canal Drive", "Lawrence", "MA", "01841"));
        ADDRESSES.add(new Address("631 Helen St.", "Westwood", "NJ", "07675"));
        ADDRESSES.add(
                new Address("768 Homewood Lane", "Voorhees", "NJ", "08043"));
        ADDRESSES
                .add(new Address("7513 Aspen Lane", "Key West", "FL", "33040"));
        ADDRESSES.add(new Address("33 Pearl Ave.", "Lake Mary", "FL", "32746"));
        ADDRESSES.add(new Address("711 Durham Drive", "Spring Valley", "NY",
                "10977"));
        ADDRESSES.add(new Address("42 Maiden Street", "Irmo", "SC", "29063"));
        ADDRESSES.add(new Address("9312 Sunnyslope Avenue", "Taunton", "MA",
                "02780"));
        ADDRESSES.add(new Address("89 Airport Court", "Saratoga Springs", "NY",
                "12866"));
        ADDRESSES.add(new Address("9977 W. Meadow Ave.", "Point Pleasant Beach",
                "NJ", "08742"));
        ADDRESSES.add(new Address("403 State Dr.", "Cranford", "NJ", "07016"));
        ADDRESSES.add(new Address("7739 South Thatcher Ave.", "Lanham", "MD",
                "20706"));
        ADDRESSES.add(
                new Address("569 Queen Ave.", "Stone Mountain", "GA", "30083"));
        ADDRESSES.add(new Address("138 Gulf St.", "Pewaukee", "WI", "53072"));
        ADDRESSES.add(
                new Address("25 Ramblewood Road", "Brandon", "FL", "33510"));
        ADDRESSES.add(
                new Address("248 Water Drive", "Greenfield", "IN", "46140"));
        ADDRESSES.add(new Address("168 Lower River Lane", "Eden Prairie", "MN",
                "55347"));
        ADDRESSES.add(new Address("9091 W. Ridge Street", "Streamwood", "IL",
                "60107"));
        ADDRESSES.add(new Address("9250 Edgewood Dr.", "Beaver Falls", "PA",
                "15010"));
        ADDRESSES.add(new Address("44 Gonzales St.", "Newtown", "PA", "18940"));
        ADDRESSES.add(
                new Address("73 Manchester Rd.", "Brockton", "MA", "02301"));
        ADDRESSES.add(
                new Address("538 53rd Ave.", "Ocean Springs", "MS", "39564"));
        ADDRESSES
                .add(new Address("79 Longfellow Lane", "Fargo", "ND", "58102"));
        ADDRESSES.add(
                new Address("9004 SE. Ridge St.", "Potomac", "MD", "20854"));
        ADDRESSES.add(
                new Address("12 Eagle Rd.", "Bowling Green", "KY", "42101"));
        ADDRESSES.add(
                new Address("737 Windsor St.", "Morganton", "NC", "28655"));
        ADDRESSES.add(new Address("8140 King Dr.", "Canfield", "OH", "44406"));
        ADDRESSES.add(new Address("78 North Oklahoma Street", "Vernon Hills",
                "IL", "60061"));
        ADDRESSES.add(new Address("8542 Riverside Lane", "West Palm Beach",
                "FL", "33404"));
        ADDRESSES.add(new Address("436 Cypress Ave.", "Upper Marlboro", "MD",
                "20772"));
        ADDRESSES.add(new Address("546 South Beacon St.", "South Portland",
                "ME", "04106"));
        ADDRESSES.add(
                new Address("17 Fordham Drive", "Englewood", "NJ", "07631"));
        ADDRESSES.add(new Address("754 South Armstrong Dr.", "Carpentersville",
                "IL", "60110"));
        ADDRESSES.add(new Address("400 Virginia Avenue", "Mason City", "IA",
                "50401"));
        ADDRESSES
                .add(new Address("714 Gonzales St.", "Munster", "IN", "46321"));
        ADDRESSES.add(new Address("9427 Shadow Brook Lane", "Westmont", "IL",
                "60559"));
        ADDRESSES.add(new Address("730 N. Henry Smith Dr.", "Wethersfield",
                "CT", "06109"));
        ADDRESSES.add(
                new Address("961 Oakland Lane", "Fairborn", "OH", "45324"));
        ADDRESSES.add(new Address("817 Arrowhead Rd.", "Green Cove Springs",
                "FL", "32043"));
        ADDRESSES.add(new Address("9800 North Belmont Ave.", "Palmetto", "FL",
                "34221"));
        ADDRESSES.add(new Address("63 Pine St.", "Snellville", "GA", "30039"));
        ADDRESSES.add(new Address("9336 West Andover Court", "Macungie", "PA",
                "18062"));
        ADDRESSES.add(
                new Address("8351 Sage Ave.", "Pittsfield", "MA", "01201"));
    }

    /* === REPORT === */

    public static Report getReport(Long id) {
        return REPORTS.get(id);
    }

    public static Collection<Report> getReports() {
        return REPORTS.values();
    }

    /* === PERSON === */

    public static Collection<Person> getPersons() {
        return PERSONS.values();
    }

    public static Person getPerson() {
        return PERSONS.get(new ArrayList<>(PERSONS.keySet())
                .get(random.nextInt(PERSONS.size())));
    }

    private static Person.Role getRole() {
        return Person.Role.values()[random
                .nextInt(Person.Role.values().length)];
    }

    /* === TRANSACTION === */

    public static Collection<Transaction> getTransactions() {
        return TRANSACTIONS.values();
    }

    private static Transaction.Status getTransactionStatus() {
        return Transaction.Status.values()[random
                .nextInt(Transaction.Status.values().length)];
    }

    /* === BANK ACCOUNT === */

    public static Collection<BankAccount> getBankAccounts() {
        return BANK_ACCOUNTS.values();
    }

    public static BankAccount getBankAccount(Long id) {
        return BANK_ACCOUNTS.get(id);
    }

    /* === PAYMENT === */

    public static Collection<Payment> getPayments() {
        return PAYMENTS.values();
    }

    private static Payment.Status getPaymentStatus() {
        return Payment.Status.values()[random
                .nextInt(Payment.Status.values().length)];
    }

    /* === MISC === */

    private static String getIBAN() {
        return IBANS[random.nextInt(IBANS.length)];
    }

    public static String[] getCompanies() {
        return COMPANIES;
    }

    public static String getCompany() {
        return COMPANIES[random.nextInt(COMPANIES.length)];
    }

    private static String getBank() {
        return BANKS[random.nextInt(BANKS.length)];
    }

    public static LocalDate getDate() {
        return LocalDate.now().minusDays(random.nextInt(20));
    }

    public static LocalDate getPastDate(int bound) {
        return LocalDate.now().minusDays(random.nextInt(bound));
    }

    public static LocalDate getFutureDate(int bound) {
        return LocalDate.now().plusDays(random.nextInt(bound));
    }

    public static String getFirstName() {
        return FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
    }

    public static String getLastName() {
        return LAST_NAMES[random.nextInt(LAST_NAMES.length)];
    }

    public static Image getLogo() {
        Image image = new Image(getImageSource(), "");
        image.setAlt("Company logo");
        return image;
    }

    public static String getImageSource() {
        return UIUtils.IMG_PATH + "logo-" + DummyData.getRandomInt(1, 40)
                + ".png";
    }

    /* === NUMBERS === */

    public static Double getAmount() {
        return random.nextBoolean() ? getNegativeAmount() : getPositiveAmount();
    }

    private static Double getPositiveAmount() {
        return random.nextDouble() * 20000;
    }

    private static Double getNegativeAmount() {
        return random.nextDouble() * -20000;
    }

    public static int getRandomInt(int min, int max) {
        return random.nextInt(max + 1 - min) + min;
    }

    public static Double getRandomDouble(int min, int max) {
        return min + (max - min) * random.nextDouble();
    }

    public static String getPhoneNumber() {
        return String.format("%09d", random.nextInt(1000000000));
    }

    /* === ITEM === */

    public static Item getRandomItem() {
        return ITEMS.get(new ArrayList<>(ITEMS.keySet())
                .get(random.nextInt(ITEMS.size())));
    }

    public static Collection<Item> getItems() {
        return ITEMS.values();
    }

    public static Collection<Item> getOrderItems() {
        ArrayList<Item> items = new ArrayList<>();
        for (int i = 0; i < getRandomInt(1, 5); i++) {
            items.add(getRandomItem());
        }
        return items;
    }

    /* === ORDER === */

    public static Order.Status getOrderStatus() {
        return Order.Status.values()[random
                .nextInt(Order.Status.values().length)];
    }

    public static Collection<Order> getOrders() {
        return ORDERS.values();
    }

    /* === INVOICE === */

    public static Invoice.Status getInvoiceStatus() {
        return Invoice.Status.values()[random
                .nextInt(Invoice.Status.values().length)];
    }

    public static Collection<Invoice> getInvoices() {
        return INVOICES.values();
    }

    /* === ADDRESS === */

    public static Address getAddress() {
        return ADDRESSES.get(random.nextInt(ADDRESSES.size()));
    }

}
