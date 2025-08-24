# Business Process Modeling with BPMN and XPDL

This repository contains the project deliverables for **Business Process Engineering** practices applied on a Patient Treatment Dataset. The project focuses on creating and analyzing Business Process Model Notation (BPMN) models, converting them to XML Process Definition Language (XPDL) format, parsing XPDL files, and simulating process variants with random activity times to calculate cycle times.

---

## 🌐 Project Overview

**Objective**: Explore BPMN and XPDL for business process modeling, convert BPMN models to XPDL, parse them for analysis, and simulate process variants by assigning random activity times to study cycle time variability.

---

## 🛠️ Project Components

### XPDL Generation and Parsing

🔹 **Core Process**: Internet Service Provision

- **Description**: Models the process of delivering internet service, including logging customer requests, verifying service availability, preparing equipment, conducting QA checks, billing, and sending welcome messages.
- **BPMN Elements**: Events, activities, gateways, and artifacts.\
  🔹 **Support Process**: Network Architecture
- **Description**: Models the creation of network architecture, including requirement analysis, design with security measures, documentation, deployment, and maintenance.\
  🔹 **XPDL Generation**: Used tools like bpmn.io to convert BPMN models to XPDL files.\
  🔹 **Parsing**: Developed a Java-based parsing tool to analyze XPDL files, extracting details like events, activities, and gateways.

### Main Project: Process Variants and Cycle Time Analysis

🔹 **Dynamic Modifications**: Implemented an `addActivityTime` algorithm in Java to assign random durations (5-15 minutes) to activities marked with "Implementation" and "Task" tags in XPDL files.\
🔹 **Cycle Time Calculation**: Computed total cycle time by analyzing activity durations and dependencies, accounting for parallel paths.\
🔹 **Process Variants**: Created multiple configurations of the process to study variability and its impact on cycle time and efficiency.\
🔹 **Insights**: Highlighted the flexibility of BPMN/XPDL in adapting to changes and the importance of precise modeling for process analysis.

---

## 🌟 Key Features

- **BPMN Models**: Detailed diagrams for core (Internet Service Provision) and support (Network Architecture) processes.
- **XPDL Parsing**: Java tool to extract and analyze BPMN elements from XPDL files.
- **Cycle Time Simulation**: Random time assignments to activities and calculation of total cycle time.
- **Process Variants**: Multiple configurations to explore process adaptability and efficiency.

---

## 📁 Project Structure

```plaintext
.
├── src/
│   ├── parser/
│   │   ├── XPDLParser.java  # Java code for parsing XPDL files
│   │   └── AddActivityTime.java  # Algorithm for assigning random times
├── models/
│   ├── core_process.bpmn    # BPMN model for Internet Service Provision
│   ├── support_process.bpmn # BPMN model for Network Architecture
│   ├── core_process.xpdl    # XPDL file for core process
│   └── support_process.xpdl # XPDL file for support process
├── output/
│   ├── parsed_results.txt   # Parsed BPMN element details
│   ├── cycle_times.txt      # Calculated cycle times for variants
├── docs/
│   ├── Analysis Report.docx # Full project report
│   └── diagrams/            # BPMN diagrams and visualizations
├── README.md                # Project documentation
└── pom.xml                  # Maven configuration for Java dependencies
```

---

## 🛠️ Installation

### Prerequisites

🔹 **Java**: JDK 8 or higher\
🔹 **Maven**: For dependency management\
🔹 **BPMN Tool**: bpmn.io or similar for BPMN to XPDL conversion

### Setup

- **Clone the Repository**:

  ```bash
  git clone https://github.com/username/bpmn-xpdl-project.git
  cd bpmn-xpdl-project
  ```

- **Install Dependencies**:

  ```bash
  mvn install
  ```

- **Run the Parser**:

  ```bash
  java -cp target/classes src.parser.XPDLParser <path-to-xpdl-file>
  ```

- **Run the Activity Time Algorithm**:

  ```bash
  java -cp target/classes src.parser.AddActivityTime <input-xpdl-file> <output-xpdl-file>
  ```

---

## 📖 Usage

- **Generate XPDL Files**: Use bpmn.io to convert BPMN models (`models/core_process.bpmn`, `models/support_process.bpmn`) to XPDL.
- **Parse XPDL**: Run `XPDLParser.java` to extract BPMN elements and save results to `output/parsed_results.txt`.
- **Simulate Process Variants**: Use `AddActivityTime.java` to assign random durations and generate process variants.
- **Calculate Cycle Times**: Compute total cycle time for each variant and save to `output/cycle_times.txt`.
- **View Results**: Check `output/` for parsed data and cycle time analysis, and `docs/diagrams/` for visualizations.

---

## 💡 Key Insights

- **BPMN/XPDL Flexibility**: Enables seamless design-to-execution transitions and adaptability to process changes.
- **Parsing Utility**: Provides detailed insights into process structure (events, activities, gateways).
- **Cycle Time Variability**: Process variants reveal how activity duration changes impact efficiency.
- **Tool Importance**: Reliable tools are critical for accurate modeling and analysis in business process management.

---

## 👨‍💻 Contributors
- **Abdullah Daoud (22I-2626)**  
- **Usman Ali (22I-2725)**  
- **Faizan Rasheed (22I-2734)**
- **Muhammad Awaimer Zaeem (22I-2616)**  
- **Azghan Ahmed (22I-2667)**  
- **Amna Asif (22I-8777)**

---

## ⚖️ License
This project is for **academic and personal skill development purposes only**.  
Reuse is allowed for **learning and research** with proper credit.

---
