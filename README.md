# SENG 438 – Assignment 3
## Code Coverage, Adequacy Criteria and Test Case Correlation

**Course:** SENG 438 - Software Testing, Reliability, and Quality  
**Group:** 08  

| Student | Name |
|---------|------|
| Student 01 | Muhammad Zain |
| Student 02 | Fateh Ali Syed Bukhari |
| Student 03 | Yazin Abdul Majid |

---

## Overview

This assignment builds directly on Assignment 2 by shifting from **black-box (requirements-based)** to **white-box (coverage-based)** testing. The system under test remains the same: `org.jfree.data.DataUtilities` and `org.jfree.data.Range` from the **JFreeChart 1.0.19** library.

The assignment is structured in three parts:

### Part 1 – Control-Flow Coverage Measurement
Using **EclEmma** (JaCoCo) inside Eclipse, we measure and report three coverage metrics for the Assignment 2 test suite:
- **Instruction coverage** (statement-level)
- **Branch coverage** (decision-level)
- **Method coverage** (used in place of condition coverage, which EclEmma does not support)

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
├── SENG438-A3.pdf                 ← Lecture slides for Assignment 3
├── 3.1 Coverages/                 ← EclEmma screenshots (baseline coverage)
│   ├── DataUtilities Instruction Coverage.png
│   ├── DataUtilities Branch Coverage.png
│   ├── DataUtilities Method Coverage.png
│   ├── Range Instruction Coverage.png
│   ├── Range Brach Coverage.png
│   └── Range Method Coverage.png
├── SENG438-A3-Group08/            ← Eclipse project (SUT + test suite)
│   ├── src/org/jfree/data/
│   │   ├── DataUtilities.java     ← SUT (contains intentional defects)
│   │   └── Range.java             ← SUT (contains intentional defects)
│   └── test/org/jfree/data/
│       ├── RangeTest.java
│       ├── DataUtilitiesMockTest.java
│       └── DataUtilitiesNonMockTest.java
└── Assignment03-Artifacts/        ← JFreeChart 1.0.19 source + JARs
```

---

## Baseline Coverage (Assignment 2 Test Suite)

| Class | Instruction Coverage | Branch Coverage | Method Coverage |
|-------|---------------------|----------------|----------------|
| `DataUtilities` | 48.0% | 35.9% | 50.0% |
| `Range` | 14.5% | 13.4% | 26.1% |

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
