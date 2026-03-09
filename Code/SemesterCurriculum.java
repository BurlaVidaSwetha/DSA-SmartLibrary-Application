import java.util.*;

/**
 * SemesterCurriculum
 * DSA concept: HashMap of HashMaps    O(1) lookup by "BRANCH_YEAR" key
 *
 * Subjects are sourced from the Digital Smart Library website's DEFAULT_SUBJECTS.
 * Each Subject maps to book keywords matched against the catalog at runtime.
 *
 * Librarian can add / remove subjects and edit their book keywords.
 */
public class SemesterCurriculum {

    //  Subject record 
    public static class Subject {
        String       code;
        String       name;
        String       icon;
        String       desc;
        List<String> bookKeywords;

        public Subject(String code, String name, String icon, String desc, String... keywords) {
            this.code         = code;
            this.name         = name;
            this.icon         = icon;
            this.desc         = desc;
            this.bookKeywords = new ArrayList<>(Arrays.asList(keywords));
        }
    }

    //  Core map: "BRANCH_YEAR" -> LinkedList of Subjects 
    private Map<String, LinkedList<Subject>> curriculumMap = new HashMap<>();

    private String key(String branch, int year) {
        return branch.toUpperCase() + "_" + year;
    }

    //  Seed from website DEFAULT_SUBJECTS 
    public void seedDefaults() {

        // 
        // CSE
        // 
        // Year 1
        add("CSE",1,new Subject("DSA1","Data Structures & Algorithms I","",
            "Arrays, Linked Lists, Stacks, Queues, basic Trees",
            "Introduction to Algorithms","Cracking","Algorithm","Data"));
        add("CSE",1,new Subject("DSA2","Data Structures & Algorithms II","",
            "Graphs, Heaps, Advanced Trees, Algorithm Design",
            "Introduction to Algorithms","Design Patterns","Algorithm"));
        add("CSE",1,new Subject("DM","Discrete Mathematics","",
            "Logic, Sets, Graph Theory, Combinatorics, Induction",
            "Discrete Mathematics","Mathematics"));
        add("CSE",1,new Subject("JAVA","Java Programming","",
            "OOP concepts, Collections, Streams, Exception Handling",
            "Clean Code","Design Patterns","Pragmatic Programmer"));
        add("CSE",1,new Subject("FWD","Front-End Web Development","",
            "HTML, CSS, JavaScript, React fundamentals",
            "System Design","Clean Code"));
        add("CSE",1,new Subject("DDCA","Digital Design for Computer Architecture","",
            "Logic Gates, Boolean Algebra, Combinational & Sequential Circuits",
            "Computer Networks","Operating System"));
        add("CSE",1,new Subject("DBMS","Database Management Systems","",
            "SQL, ER Diagrams, Normalization, Transactions, ACID",
            "Database System Concepts","Database"));
        add("CSE",1,new Subject("MDSA","Mathematics for Data Science & Analytics","",
            "Statistics, Probability, Linear Algebra for ML/DS",
            "Linear Algebra","AI","Python Crash Course"));
        add("CSE",1,new Subject("AOOP","Advanced Object Oriented Programming","",
            "Design Patterns, SOLID Principles, Generics, Reflection",
            "Design Patterns","Clean Code","Pragmatic Programmer"));
        add("CSE",1,new Subject("AI","Artificial Intelligence","",
            "Search Algorithms, Knowledge Representation, ML Basics, NLP",
            "Artificial Intelligence","AI: A Modern Approach","AI"));

        // Year 2
        add("CSE",2,new Subject("OS","Operating Systems","",
            "Processes, Threads, Scheduling, Memory Management, File Systems",
            "Operating System Concepts","Operating System"));
        add("CSE",2,new Subject("CN","Computer Networks","",
            "OSI Model, TCP/IP, DNS, HTTP, Routing Protocols",
            "Computer Networks"));
        add("CSE",2,new Subject("TOC","Theory of Computation","",
            "Automata, Grammars, Turing Machines, Complexity Classes",
            "Introduction to Algorithms","Algorithm"));
        add("CSE",2,new Subject("SE","Software Engineering","",
            "SDLC, Agile, UML, Testing, Project Management",
            "Pragmatic Programmer","Clean Code","Software"));
        add("CSE",2,new Subject("DAA","Design & Analysis of Algorithms","",
            "Greedy, Divide & Conquer, Dynamic Programming, NP-Completeness",
            "Introduction to Algorithms","Cracking","Algorithm"));
        add("CSE",2,new Subject("COA","Computer Organization & Architecture","",
            "CPU Design, Pipelining, Cache Memory, I/O Systems",
            "Computer Networks","Operating System"));
        add("CSE",2,new Subject("MP","Microprocessors & Embedded Systems","",
            "8086 Architecture, Assembly, Interfacing, Embedded C",
            "Computer Networks","System Design"));
        add("CSE",2,new Subject("EVS","Environmental Science","",
            "Ecology, Pollution, Sustainability, Environmental Laws",
            "Deep Work","Atomic Habits"));

        // Year 3
        add("CSE",3,new Subject("ML","Machine Learning","",
            "Regression, Classification, Clustering, Feature Engineering",
            "Artificial Intelligence","AI: A Modern Approach","Python Crash Course"));
        add("CSE",3,new Subject("DL","Deep Learning","",
            "Neural Networks, CNN, RNN, Transformers, GANs",
            "Artificial Intelligence","AI: A Modern Approach"));
        add("CSE",3,new Subject("CC","Cloud Computing","",
            "AWS/Azure/GCP, Virtualization, Containers, Serverless",
            "System Design Interview","System Design"));
        add("CSE",3,new Subject("IS","Information Security","",
            "Cryptography, Network Security, Ethical Hacking basics",
            "Computer Networks","System Design"));
        add("CSE",3,new Subject("WD","Web Development (Full Stack)","",
            "Node.js, React, REST APIs, MongoDB, Deployment",
            "Clean Code","System Design Interview","Pragmatic Programmer"));
        add("CSE",3,new Subject("DC","Distributed Computing","",
            "Map-Reduce, Consensus Algorithms, Fault Tolerance",
            "System Design Interview","System Design"));
        add("CSE",3,new Subject("CD","Compiler Design","",
            "Lexical Analysis, Parsing, Semantic Analysis, Code Generation",
            "Introduction to Algorithms","Algorithm"));
        add("CSE",3,new Subject("PE","Professional Ethics","",
            "Engineering Ethics, Intellectual Property, Cyber Law",
            "Deep Work","Atomic Habits","Soft Skills"));

        // Year 4
        add("CSE",4,new Subject("BD","Big Data Analytics","",
            "Hadoop, Spark, HDFS, Data Pipelines, Real-time Processing",
            "Database System Concepts","Python Crash Course"));
        add("CSE",4,new Subject("IOT","Internet of Things","",
            "IoT Architecture, Sensors, Protocols, Edge Computing",
            "System Design","Computer Networks"));
        add("CSE",4,new Subject("BC","Blockchain Technology","",
            "Consensus, Smart Contracts, Ethereum, DeFi basics",
            "System Design Interview","Zero to One"));
        add("CSE",4,new Subject("CV","Computer Vision","",
            "Image Processing, Object Detection, OpenCV, YOLO",
            "Artificial Intelligence","AI: A Modern Approach","Python Crash Course"));
        add("CSE",4,new Subject("NLP","Natural Language Processing","",
            "Tokenization, Transformers, BERT, Sentiment Analysis",
            "Artificial Intelligence","AI: A Modern Approach"));
        add("CSE",4,new Subject("SD","Software Design & Architecture","",
            "Microservices, Design Patterns, System Design, APIs",
            "System Design Interview","Design Patterns","Clean Code"));
        add("CSE",4,new Subject("RP","Research Project / Capstone","",
            "Industry-level project spanning the final year",
            "Cracking","System Design Interview","Elements of Programming"));
        add("CSE",4,new Subject("EL","Elective: Advanced Topics","",
            "AR/VR, Quantum Computing, Autonomous Systems",
            "Artificial Intelligence","System Design Interview"));

        // 
        // CSIT
        // 
        // Year 1
        add("CSIT",1,new Subject("DSA1","Data Structures & Algorithms I","",
            "Arrays, Linked Lists, Stacks, Queues, Trees basics",
            "Introduction to Algorithms","Cracking","Algorithm"));
        add("CSIT",1,new Subject("DSA2","Data Structures & Algorithms II","",
            "Graphs, Heaps, Advanced Trees, Algorithm Design",
            "Introduction to Algorithms","Algorithm"));
        add("CSIT",1,new Subject("DM","Discrete Mathematics","",
            "Logic, Sets, Graph Theory, Combinatorics, Induction",
            "Discrete Mathematics","Mathematics"));
        add("CSIT",1,new Subject("JAVA","Java Programming","",
            "OOP concepts, Collections, Streams, Exception Handling",
            "Clean Code","Design Patterns","Pragmatic Programmer"));
        add("CSIT",1,new Subject("FWD","Front-End Web Development","",
            "HTML, CSS, JavaScript, React fundamentals",
            "System Design","Clean Code"));
        add("CSIT",1,new Subject("DDCA","Digital Design for Computer Architecture","",
            "Logic Gates, Boolean Algebra, Sequential Circuits",
            "Computer Networks","Operating System"));
        add("CSIT",1,new Subject("DBMS","Database Management Systems","",
            "SQL, ER Diagrams, Normalization, Transactions",
            "Database System Concepts","Database"));
        add("CSIT",1,new Subject("MDSA","Mathematics for Data Science & Analytics","",
            "Statistics, Probability, Linear Algebra for ML/DS",
            "Linear Algebra","Python Crash Course","AI"));
        add("CSIT",1,new Subject("AOOP","Advanced Object Oriented Programming","",
            "Design Patterns, SOLID Principles, Generics",
            "Design Patterns","Clean Code","Pragmatic Programmer"));
        add("CSIT",1,new Subject("AI","Artificial Intelligence","",
            "Search Algorithms, Knowledge Representation, ML Basics",
            "Artificial Intelligence","AI: A Modern Approach","AI"));

        // Year 2
        add("CSIT",2,new Subject("OS","Operating Systems","",
            "Processes, Scheduling, Memory Management, File Systems",
            "Operating System Concepts","Operating System"));
        add("CSIT",2,new Subject("CN","Computer Networks","",
            "OSI Model, TCP/IP, Routing, Network Security",
            "Computer Networks"));
        add("CSIT",2,new Subject("SE","Software Engineering","",
            "SDLC, Agile, UML, Testing Methodologies",
            "Pragmatic Programmer","Clean Code"));
        add("CSIT",2,new Subject("DAA","Design & Analysis of Algorithms","",
            "Greedy, Dynamic Programming, NP-Completeness",
            "Introduction to Algorithms","Algorithm","Cracking"));
        add("CSIT",2,new Subject("IT","Information Theory","",
            "Entropy, Coding, Data Compression, Channel Capacity",
            "Computer Networks","Discrete Mathematics"));
        add("CSIT",2,new Subject("WT","Web Technologies","",
            "HTML5, CSS3, JavaScript, AJAX, Web Frameworks",
            "Clean Code","System Design","You Don't Know JS"));
        add("CSIT",2,new Subject("PM","Project Management","",
            "Agile, Scrum, Risk Management, Cost Estimation",
            "Pragmatic Programmer","Deep Work","Soft Skills"));
        add("CSIT",2,new Subject("DW","Data Warehousing","",
            "ETL, OLAP, Star Schema, Data Marts",
            "Database System Concepts","Database"));

        // Year 3
        add("CSIT",3,new Subject("ML","Machine Learning","",
            "Supervised, Unsupervised, Feature Engineering, Model Evaluation",
            "Artificial Intelligence","AI: A Modern Approach","Python Crash Course"));
        add("CSIT",3,new Subject("DS","Data Science","",
            "Pandas, NumPy, EDA, Visualization, Statistical Inference",
            "Python Crash Course","Linear Algebra"));
        add("CSIT",3,new Subject("CC","Cloud Computing","",
            "Cloud Services, Virtualization, Containers, DevOps",
            "System Design Interview","System Design"));
        add("CSIT",3,new Subject("IS","Information Security","",
            "Cryptography, PKI, Firewalls, Penetration Testing",
            "Computer Networks","System Design"));
        add("CSIT",3,new Subject("MO","Mobile App Development","",
            "Android/iOS, Flutter, React Native, App Lifecycle",
            "Clean Code","System Design","Pragmatic Programmer"));
        add("CSIT",3,new Subject("BA","Business Analytics","",
            "Tableau, Power BI, KPIs, Predictive Analytics",
            "Python Crash Course","Deep Work","Zero to One"));
        add("CSIT",3,new Subject("NLP","Natural Language Processing","",
            "Text Processing, Sentiment Analysis, BERT, ChatBots",
            "Artificial Intelligence","AI: A Modern Approach"));
        add("CSIT",3,new Subject("PE","Professional Ethics & Cyber Law","",
            "IT Act, Data Privacy, Ethics in AI, Cybercrime",
            "Deep Work","Atomic Habits","Soft Skills"));

        // Year 4
        add("CSIT",4,new Subject("BD","Big Data Technologies","",
            "Hadoop, Spark, Kafka, Hive, Real-time Streaming",
            "Database System Concepts","Python Crash Course"));
        add("CSIT",4,new Subject("AI2","Advanced AI & Deep Learning","",
            "CNN, LSTM, GAN, Reinforcement Learning, AutoML",
            "Artificial Intelligence","AI: A Modern Approach"));
        add("CSIT",4,new Subject("BC","Blockchain & Fintech","",
            "DLT, Smart Contracts, Cryptocurrency, DeFi",
            "System Design Interview","Zero to One"));
        add("CSIT",4,new Subject("CY","Cybersecurity Management","",
            "SIEM, SOC, Incident Response, ISO 27001",
            "Computer Networks","System Design"));
        add("CSIT",4,new Subject("ERP","Enterprise Resource Planning","",
            "SAP, Oracle ERP, ERP Implementation, Business Processes",
            "Database System Concepts","Zero to One","Art of the Start"));
        add("CSIT",4,new Subject("RP","Research Project / Capstone","",
            "Industry-level project spanning the final year",
            "Cracking","System Design Interview","Elements of Programming"));
        add("CSIT",4,new Subject("INT","Internship & Industry Training","",
            "Real-world exposure, Industry mentoring, Report submission",
            "Soft Skills","What Color Is Your Parachute","Zero to One"));
        add("CSIT",4,new Subject("EL","Elective: Emerging Technologies","",
            "Quantum Computing, AR/VR, Digital Twin, Edge AI",
            "Artificial Intelligence","System Design Interview"));

        // 
        // ECE
        // 
        // Year 1
        add("ECE",1,new Subject("BEE","Basic Electrical Engineering","",
            "Circuits, Ohm's Law, KVL/KCL, AC/DC Analysis",
            "Computer Networks","Operating System"));
        add("ECE",1,new Subject("EDT","Electronic Devices & Theory","",
            "Diodes, Transistors, MOSFETs, Operational Amplifiers",
            "Computer Networks","System Design"));
        add("ECE",1,new Subject("DM","Discrete Mathematics","",
            "Logic, Sets, Graph Theory, Combinatorics",
            "Discrete Mathematics","Mathematics"));
        add("ECE",1,new Subject("DDCA","Digital Design for Computer Architecture","",
            "Logic Gates, Flip-Flops, Counters, Multiplexers",
            "Computer Networks","Database System"));
        add("ECE",1,new Subject("PIC","Programming for IoT & Control","",
            "C/Python, Embedded basics, GPIO control",
            "Python Crash Course","System Design"));
        add("ECE",1,new Subject("SS","Signals & Systems","",
            "Fourier, Laplace, Z-Transform, Convolution",
            "Introduction to Algorithms","Linear Algebra"));
        add("ECE",1,new Subject("MDSA","Mathematics for Data Science & Analytics","",
            "Statistics, Probability, Linear Algebra",
            "Linear Algebra","Discrete Mathematics","Python Crash Course"));
        add("ECE",1,new Subject("EM","Engineering Mathematics","",
            "Calculus, Differential Equations, Complex Analysis",
            "Discrete Mathematics","Linear Algebra"));
        add("ECE",1,new Subject("FWD","Front-End Web Development","",
            "HTML, CSS, JavaScript basics",
            "Clean Code","System Design"));
        add("ECE",1,new Subject("AI","Artificial Intelligence Fundamentals","",
            "AI basics, Search Algorithms, ML introduction",
            "Artificial Intelligence","AI: A Modern Approach","AI"));

        // Year 2
        add("ECE",2,new Subject("AEC","Analog Electronics Circuits","",
            "Amplifiers, Oscillators, Feedback, Power Amplifiers",
            "Computer Networks","System Design"));
        add("ECE",2,new Subject("DEC","Digital Electronics & Circuits","",
            "Combinational & Sequential Logic, State Machines",
            "Computer Networks","Operating System"));
        add("ECE",2,new Subject("MP","Microprocessors & Microcontrollers","",
            "8051, ARM, Arduino, RTOS basics",
            "Computer Networks","System Design Interview"));
        add("ECE",2,new Subject("EMT","Electromagnetic Theory","",
            "Maxwell's Equations, Wave Propagation, Antennas",
            "Computer Networks","Linear Algebra"));
        add("ECE",2,new Subject("CT","Communication Theory","",
            "AM/FM/PM, Digital Modulation, BER, Noise Analysis",
            "Computer Networks","Algorithm"));
        add("ECE",2,new Subject("CN","Computer Networks","",
            "OSI Model, TCP/IP, Routing, Wireless Protocols",
            "Computer Networks"));
        add("ECE",2,new Subject("DBMS","Database Management Systems","",
            "SQL, ER Diagrams, Normalization, Transactions",
            "Database System Concepts","Database"));
        add("ECE",2,new Subject("EVS","Environmental Science","",
            "Ecology, E-waste, Green Electronics, Sustainability",
            "Deep Work","Atomic Habits"));

        // Year 3
        add("ECE",3,new Subject("VLSI","VLSI Design","",
            "CMOS, Layout, Verilog, FPGA, System on Chip",
            "Computer Networks","System Design","Algorithm"));
        add("ECE",3,new Subject("DSP","Digital Signal Processing","",
            "FIR/IIR Filters, FFT, Spectral Analysis, Real-time DSP",
            "Introduction to Algorithms","Linear Algebra"));
        add("ECE",3,new Subject("ES","Embedded Systems","",
            "ARM Cortex, RTOS, Device Drivers, Peripheral Interfacing",
            "System Design","Computer Networks","Python Crash Course"));
        add("ECE",3,new Subject("IOT","Internet of Things","",
            "IoT Protocols, MQTT, AWS IoT, Edge Intelligence",
            "System Design Interview","Computer Networks"));
        add("ECE",3,new Subject("WC","Wireless Communications","",
            "4G/5G, OFDM, MIMO, Channel Coding",
            "Computer Networks","Algorithm"));
        add("ECE",3,new Subject("OFC","Optical Fiber Communications","",
            "Fiber Types, WDM, Optical Amplifiers, Networks",
            "Computer Networks","System Design"));
        add("ECE",3,new Subject("AI","AI for ECE Applications","",
            "ML for Signal Processing, Edge AI, TinyML",
            "Artificial Intelligence","AI: A Modern Approach","Python Crash Course"));
        add("ECE",3,new Subject("PE","Professional Ethics","",
            "Engineering Ethics, Intellectual Property, IEEE Standards",
            "Deep Work","Atomic Habits","Soft Skills"));

        // Year 4
        add("ECE",4,new Subject("VLSI2","Advanced VLSI & Chip Design","",
            "FinFET, 3D IC, Physical Verification, Timing Closure",
            "System Design Interview","Algorithm"));
        add("ECE",4,new Subject("RF","RF & Microwave Engineering","",
            "S-Parameters, Waveguides, Radar, Satellite Systems",
            "Computer Networks","System Design"));
        add("ECE",4,new Subject("ROB","Robotics & Control Systems","",
            "PID, State-Space, Servo Motors, Robot Kinematics",
            "Artificial Intelligence","Python Crash Course","Algorithm"));
        add("ECE",4,new Subject("MED","Medical Electronics","",
            "ECG, EEG, Imaging Systems, Biomedical Sensors",
            "AI: A Modern Approach","Python Crash Course"));
        add("ECE",4,new Subject("AV","Autonomous Vehicles & ADAS","",
            "LiDAR, Camera Fusion, Path Planning, Safety Standards",
            "Artificial Intelligence","System Design Interview","Algorithm"));
        add("ECE",4,new Subject("RP","Research Project / Capstone","",
            "Industry-level project spanning the final year",
            "Cracking","System Design Interview","Pragmatic Programmer"));
        add("ECE",4,new Subject("EL","Elective: Advanced Topics","",
            "Quantum Electronics, Photonics, Space Technology",
            "Artificial Intelligence","System Design Interview"));
        add("ECE",4,new Subject("INT","Internship & Industry Training","",
            "Real-world industry exposure and mentoring",
            "Soft Skills","What Color Is Your Parachute","Zero to One"));

        // 
        // AIDS
        // 
        // Year 1
        add("AIDS",1,new Subject("DSA1","Data Structures & Algorithms I","",
            "Arrays, Linked Lists, Stacks, Queues, Trees basics",
            "Introduction to Algorithms","Cracking","Algorithm"));
        add("AIDS",1,new Subject("DSA2","Data Structures & Algorithms II","",
            "Graphs, Heaps, Advanced Trees, Algorithm Design",
            "Introduction to Algorithms","Algorithm"));
        add("AIDS",1,new Subject("DM","Discrete Mathematics","",
            "Logic, Sets, Graph Theory, Combinatorics, Induction",
            "Discrete Mathematics","Mathematics"));
        add("AIDS",1,new Subject("JAVA","Java Programming","",
            "OOP, Collections, Streams, Exception Handling",
            "Clean Code","Design Patterns","Pragmatic Programmer"));
        add("AIDS",1,new Subject("FWD","Front-End Web Development","",
            "HTML, CSS, JavaScript, React for dashboards",
            "System Design","Clean Code","You Don't Know JS"));
        add("AIDS",1,new Subject("DDCA","Digital Design for Computer Architecture","",
            "Logic Gates, Boolean Algebra, Sequential Circuits",
            "Computer Networks","Operating System"));
        add("AIDS",1,new Subject("DBMS","Database Management Systems","",
            "SQL, NoSQL, ER Diagrams, Normalization",
            "Database System Concepts","Database"));
        add("AIDS",1,new Subject("MDSA","Mathematics for Data Science & Analytics","",
            "Statistics, Probability, Linear Algebra, Calculus",
            "Linear Algebra","Discrete Mathematics","Python Crash Course"));
        add("AIDS",1,new Subject("AOOP","Advanced Object Oriented Programming","",
            "Design Patterns, SOLID, Generics, Reflection",
            "Design Patterns","Clean Code","Pragmatic Programmer"));
        add("AIDS",1,new Subject("AI","Artificial Intelligence Foundations","",
            "Search, Knowledge Representation, ML basics, Ethics in AI",
            "Artificial Intelligence","AI: A Modern Approach","AI"));

        // Year 2
        add("AIDS",2,new Subject("ML","Machine Learning","",
            "Regression, Classification, SVM, Random Forests",
            "Artificial Intelligence","AI: A Modern Approach","Python Crash Course"));
        add("AIDS",2,new Subject("DS","Data Science with Python","",
            "Pandas, NumPy, Matplotlib, Seaborn, Scikit-Learn",
            "Python Crash Course","Linear Algebra"));
        add("AIDS",2,new Subject("STA","Statistics & Probability","",
            "Bayesian Inference, Hypothesis Testing, Distributions",
            "Discrete Mathematics","Linear Algebra","Thinking Fast and Slow"));
        add("AIDS",2,new Subject("DV","Data Visualization","",
            "Tableau, Power BI, Plotly, Storytelling with Data",
            "Python Crash Course","Thinking Fast and Slow"));
        add("AIDS",2,new Subject("OS","Operating Systems","",
            "Processes, Memory, File Systems, Shell Scripting",
            "Operating System Concepts","Operating System"));
        add("AIDS",2,new Subject("CN","Computer Networks","",
            "OSI, TCP/IP, Web Protocols, API Communication",
            "Computer Networks"));
        add("AIDS",2,new Subject("SE","Software Engineering","",
            "Agile, SDLC, Version Control, CI/CD",
            "Pragmatic Programmer","Clean Code"));
        add("AIDS",2,new Subject("BA","Business Analytics","",
            "KPIs, Dashboards, Market Analysis, Decision Science",
            "Zero to One","Thinking Fast and Slow","Deep Work"));

        // Year 3
        add("AIDS",3,new Subject("DL","Deep Learning","",
            "CNN, RNN, LSTM, Transformers, Transfer Learning",
            "Artificial Intelligence","AI: A Modern Approach"));
        add("AIDS",3,new Subject("BD","Big Data Technologies","",
            "Hadoop, Spark, Kafka, HDFS, Hive",
            "Database System Concepts","Python Crash Course"));
        add("AIDS",3,new Subject("NLP","Natural Language Processing","",
            "Tokenization, Word2Vec, BERT, GPT, Chatbots",
            "Artificial Intelligence","AI: A Modern Approach"));
        add("AIDS",3,new Subject("CV","Computer Vision","",
            "Image Classification, Object Detection, Segmentation",
            "Artificial Intelligence","Python Crash Course"));
        add("AIDS",3,new Subject("RL","Reinforcement Learning","",
            "Q-Learning, Policy Gradient, OpenAI Gym, Game AI",
            "Artificial Intelligence","AI: A Modern Approach","Algorithm"));
        add("AIDS",3,new Subject("CC","Cloud & MLOps","",
            "AWS SageMaker, Azure ML, Docker, Kubernetes, MLflow",
            "System Design Interview","System Design"));
        add("AIDS",3,new Subject("XAI","Explainable AI & Ethics","",
            "SHAP, LIME, Fairness, AI Regulation, Bias Mitigation",
            "Artificial Intelligence","Thinking Fast and Slow","Deep Work"));
        add("AIDS",3,new Subject("TS","Time Series Analysis","",
            "ARIMA, LSTM for Time Series, Forecasting, Anomaly Detection",
            "Python Crash Course","Algorithm","AI: A Modern Approach"));

        // Year 4
        add("AIDS",4,new Subject("GAI","Generative AI & LLMs","",
            "GPT, Diffusion Models, LoRA, Prompt Engineering, RAG",
            "Artificial Intelligence","AI: A Modern Approach"));
        add("AIDS",4,new Subject("RA","Recommender Systems","",
            "Collaborative Filtering, Content-Based, Hybrid Systems",
            "Artificial Intelligence","Python Crash Course","Algorithm"));
        add("AIDS",4,new Subject("FE","Feature Engineering & AutoML","",
            "Feature Selection, AutoML, Hyperparameter Tuning",
            "Python Crash Course","AI: A Modern Approach","Algorithm"));
        add("AIDS",4,new Subject("GNN","Graph Neural Networks","",
            "GCN, GraphSAGE, Knowledge Graphs, Social Network Analysis",
            "Introduction to Algorithms","Artificial Intelligence","Algorithm"));
        add("AIDS",4,new Subject("DE","Data Engineering","",
            "Data Pipelines, dbt, Airflow, Lakehouse Architecture",
            "Database System Concepts","System Design Interview","Python Crash Course"));
        add("AIDS",4,new Subject("IOT_AI","AI for IoT & Edge","",
            "TinyML, Edge Inference, Federated Learning",
            "Artificial Intelligence","System Design","Python Crash Course"));
        add("AIDS",4,new Subject("RP","Research Project / Capstone","",
            "Industry-level AI/DS project spanning the final year",
            "Cracking","System Design Interview","Elements of Programming"));
        add("AIDS",4,new Subject("INT","Internship & Industry Training","",
            "Real-world data science and AI industry exposure",
            "Soft Skills","Zero to One","What Color Is Your Parachute"));
    }

    //  CRUD 
    public void add(String branch, int year, Subject subject) {
        String k = key(branch, year);
        curriculumMap.putIfAbsent(k, new LinkedList<>());
        curriculumMap.get(k).add(subject);
    }

    public LinkedList<Subject> getSubjects(String branch, int year) {
        return curriculumMap.getOrDefault(key(branch, year), new LinkedList<>());
    }

    public boolean removeSubject(String branch, int year, String subjectName) {
        LinkedList<Subject> list = curriculumMap.get(key(branch, year));
        if (list == null) return false;
        return list.removeIf(s -> s.name.equalsIgnoreCase(subjectName) || s.code.equalsIgnoreCase(subjectName));
    }

    public boolean addKeywordToSubject(String branch, int year, String subjectCode, String keyword) {
        for (Subject s : getSubjects(branch, year)) {
            if (s.code.equalsIgnoreCase(subjectCode) || s.name.equalsIgnoreCase(subjectCode)) {
                if (!s.bookKeywords.contains(keyword)) s.bookKeywords.add(keyword);
                return true;
            }
        }
        return false;
    }

    public boolean removeKeywordFromSubject(String branch, int year, String subjectCode, String keyword) {
        for (Subject s : getSubjects(branch, year)) {
            if (s.code.equalsIgnoreCase(subjectCode) || s.name.equalsIgnoreCase(subjectCode)) {
                return s.bookKeywords.remove(keyword);
            }
        }
        return false;
    }

    public void displayAllCurriculum() {
        String[] branches = {"CSE","CSIT","ECE","AIDS"};
        System.out.println("  +== FULL SEMESTER CURRICULUM (synced with website) ==+");
        for (String br : branches) {
            for (int yr = 1; yr <= 4; yr++) {
                LinkedList<Subject> subs = curriculumMap.get(key(br, yr));
                if (subs == null || subs.isEmpty()) continue;
                System.out.println("\n  >> " + br + " | Year " + yr + " (" + subs.size() + " subjects)");
                System.out.println("     " + "-".repeat(60));
                for (Subject s : subs) {
                    System.out.println("     [" + s.code + "] " + s.icon + " " + s.name);
                    System.out.println("           " + s.desc);
                    System.out.println("           Keywords: " + s.bookKeywords);
                    System.out.println("     " + "-".repeat(60));
                }
            }
        }
    }

    public Set<String> allKeys() { return curriculumMap.keySet(); }
}
