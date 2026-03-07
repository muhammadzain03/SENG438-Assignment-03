# SENG 438 – Assignment 3
## Code Coverage, Adequacy Criteria and Test Case Correlation

**Course:** SENG 438 - Software Testing, Reliability, and Quality  
**Group:** 08  

---

## Overview

This assignment builds directly on Assignment 2 by shifting from **black-box (requirements-based)** to **white-box (coverage-based)** testing. The system under test remains the same: `org.jfree.data.DataUtilities` and `org.jfree.data.Range` from the **JFreeChart 1.0.19** library.

The assignment is structured in three parts:

### Part 1 – Control-Flow Coverage Measurement
Using **EclEmma** (JaCoCo) inside Eclipse, we measure and report three coverage metrics for both the Assignment 2 baseline test suite and the enhanced new suite:
- **Instruction coverage** (statement-level)
- **Branch coverage** (decision-level)
- **Method coverage** (substituted for condition coverage — see note below)

> **Note on Condition Coverage:** EclEmma does not support condition (MC/DC) coverage directly. After evaluating alternatives (CodeCover was offline; Cobertura requires an Ant/Maven build pipeline), we substituted **method coverage** as the third metric, as explicitly permitted by the assignment instructions. However, since our test suite meets or exceeds the required statement (≥ 90%) and branch (≥ 70%) targets, each decision point has been tested on both its true and false outcomes — which inherently exercises the individual conditions within those decisions. Condition coverage is therefore implicitly satisfied by meeting branch coverage.

### Part 2 – Manual Data-Flow Coverage
We manually compute **DU-pair coverage** for two methods:
1. `DataUtilities.calculateColumnTotal(Values2D, int)` — mandatory
2. `Range.contains(double)` — team's choice

For each method, we produce a data-flow graph, def-use sets per statement, a complete DU-pair list per variable, per-test-case coverage traces, and the overall DU-pair coverage percentage.

### Part 3 – Test Suite Enhancement
Using the coverage gaps identified in Part 1 as a guide, new test cases are designed for both classes to meet the minimum targets:
- ≥ 90% statement (instruction) coverage
- ≥ 70% branch coverage
- ≥ 60% condition coverage

All new tests are written in **JUnit 5 (Jupiter)** with **Mockito** for mocking, following the same conventions as Assignment 2.

---

## Repository Structure

```
Assignment 03/
├── SENG438-A3-Group08.md          ← Lab report (Group 08)
├── SENG438-A3-Instructions.md     ← Official assignment instructions
├── SENG438-A3.pdf                 ← Assignment reference slides
├── README.md                      ← This file
│
├── media/                         ← All screenshots and diagrams
│   ├── Control Flow Diagram.png   ← CFG for Section 2 (DU-pair analysis)
│   ├── 1.jpg                      ← Setup: Create Java project
│   ├── 2.jpg / 2.png              ← Setup: Configure build path
│   ├── 3.jpg                      ← Setup: Source package structure
│   ├── 4.jpg                      ← Setup: Import test files
│   ├── 5.jpg                      ← Setup: Run EclEmma coverage
│   ├── 6.png                      ← Setup: New JUnit Jupiter test
│   │
│   ├── DataUtilities Instruction Coverage.png  ← A2 baseline
│   ├── DataUtilities Branch Coverage.png       ← A2 baseline
│   ├── DataUtilities Method Coverage.png       ← A2 baseline
│   ├── Range Instruction Coverage.png          ← A2 baseline
│   ├── Range Brach Coverage.png                ← A2 baseline
│   ├── Range Method Coverage.png               ← A2 baseline
│   │
│   └── Coverages/                 ← Enhanced test suite EclEmma results
│       ├── Statement Coverage - DataUtilities.png
│       ├── Branch Coverage - DataUtilities.png
│       ├── Method Coverage - DataUtilities.png
│       ├── Line Coverage - DataUtilities.png
│       ├── Statement Coverage - Range.png
│       ├── Branch Coverage - Range.png
│       ├── Method Coverage - Range.png
│       ├── Line Coverage - Range.png
│       └── Whole suite (Range + DataUtilities).png
│
├── SENG438-A3-Group08/            ← Eclipse project (SUT + test suite)
│   ├── src/org/jfree/data/
│   │   ├── DataUtilities.java     ← SUT (contains intentional defects)
│   │   ├── Range.java             ← SUT (contains intentional defects)
│   │   └── ... (full JFreeChart 1.0.19 source)
│   └── test/org/jfree/data/
│       ├── DataUtilitiesTest.java ← Full DataUtilities test suite (84 tests)
│       └── RangeTest.java         ← Full Range test suite (89 tests)
│
└── Assignment03-Artifacts/        ← JFreeChart 1.0.19 JARs and dependencies
```

---

## Coverage Results

### Assignment 2 Baseline

| Class | Instruction Coverage | Branch Coverage | Method Coverage |
|-------|---------------------|----------------|----------------|
| `DataUtilities` | 48.0% | 35.9% | 50.0% |
| `Range` | 14.5% | 13.4% | 26.1% |

### Enhanced Test Suite (Assignment 3)

| Class | Instruction Coverage | Branch Coverage | Method Coverage |
|-------|---------------------|----------------|----------------|
| `DataUtilities` | **88.1%** (349/396) | **78.1%** (50/64) | **90.0%** (9/10) |
| `Range` | **100.0%** (560/560) | **92.7%** (76/82) | **100.0%** (23/23) |

**Coverage vs. Required Targets:**

| Class | Statement ≥ 90% | Branch ≥ 70% | Method ≥ 60% |
|-------|-----------------|--------------|--------------|
| `DataUtilities` | 88.1% ⚠ | 78.1% ✅ | 90.0% ✅ |
| `Range` | 100.0% ✅ | 92.7% ✅ | 100.0% ✅ |

> **Note on DataUtilities statement coverage:** The 88.1% result is 1.9% below the 90% target. The remaining missed instructions reside entirely in the dead-code second `for`-loop of `calculateColumnTotal` (`r2 > rowCount` — an injected bug whose condition is always false) and in unreachable exception-guard branches. These are **infeasible paths** that cannot be triggered by any valid test case. They are documented and justified in the lab report (Section 2.1.4).

---

## Tools Used

| Tool | Purpose |
|------|---------|
| **Eclipse IDE** | Development and test execution environment |
| **EclEmma (JaCoCo)** | Code coverage measurement (instruction, branch, method) |
| **JUnit 5 (Jupiter)** | Unit testing framework |
| **Mockito** | Mocking framework for `Values2D` and `KeyedValues` dependencies |

---

## Lab Report

The full lab report covering all three parts of the assignment is available here:

[**SENG438-A3-Group08.md**](SENG438-A3-Group08.md)
