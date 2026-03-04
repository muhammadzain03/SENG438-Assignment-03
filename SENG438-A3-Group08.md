## **SENG 438 - Software Testing, Reliability, and Quality**

## **Lab Report #3 – Code Coverage, Adequacy Criteria and Test Case Correlation**

| Group #: | 08 |
|--------|----|
| Student Name 01 | Muhammad Zain |
| Student Name 02 | Fateh Ali Syed Bukhari |
| Student Name 03 | Yazin Abdul Majid |

---

# 1 Introduction

This lab builds directly on Assignment 2 by shifting the testing strategy from black-box (requirements-based) to white-box (coverage-based) testing. Where Assignment 2 relied solely on Javadoc specifications to derive test oracles, this assignment leverages the source code itself to evaluate test suite adequacy through control-flow and data-flow coverage metrics.

The system under test remains the same: `org.jfree.data.DataUtilities` and `org.jfree.data.Range` from the JFreeChart 1.0.19 library. These classes contain intentionally injected defects, meaning some tests are expected to fail even when correctly written against the specification.

The assignment is structured in three parts:

1. **Control-flow coverage measurement** — Using EclEmma inside Eclipse, we measure instruction (statement), branch, and method coverage of our Assignment 2 test suite against both classes and report the results in detail.
2. **Manual data-flow coverage** — We manually trace the execution of our existing test cases through two selected methods to compute DU-pair coverage by hand, without relying on any tool.
3. **Test suite enhancement** — We design new test cases guided by the coverage gaps identified in Part 1, targeting the minimums of 90% statement, 70% branch, and 60% condition coverage per class.

---

# 2 Manual Data-Flow Coverage Calculations for `DataUtilities.calculateColumnTotal` and `Range.contains`

> **Note:** This section is a rough draft. Full data-flow graphs, def-use sets, DU-pair tables, and per-test coverage traces will be completed here.

## 2.1 `DataUtilities.calculateColumnTotal(Values2D data, int column)`

### Source Code Under Analysis

```java
public static double calculateColumnTotal(Values2D data, int column) {
    ParamChecks.nullNotPermitted(data, "data");  // line 1
    double total = 0.0;                           // line 2
    int rowCount = data.getRowCount();            // line 3
    for (int r = 0; r < rowCount; r++) {          // line 4
        Number n = data.getValue(r, column);      // line 5
        if (n != null) {                          // line 6
            total += n.doubleValue();             // line 7
        }
    }
    for (int r2 = 0; r2 > rowCount; r2++) {       // line 8 (dead code – bug)
        Number n = data.getValue(r2, column);     // line 9
        if (n != null) {                          // line 10
            total += n.doubleValue();             // line 11
        }
    }
    return total;                                  // line 12
}
```

### Data-Flow Graph

```
[1] nullNotPermitted
     ↓
[2] total = 0.0
     ↓
[3] rowCount = data.getRowCount()
     ↓
[4] r = 0; r < rowCount?  ──No──→ [8] r2 = 0; r2 > rowCount? ──No──→ [12] return total
     │Yes
     ↓
[5] n = data.getValue(r, column)
     ↓
[6] n != null?  ──No──→ [4] r++
     │Yes
     ↓
[7] total += n.doubleValue()  ──→ [4] r++
```
(The second loop at line 8 is dead code because `r2 > rowCount` is false when `r2 = 0` and `rowCount ≥ 0`.)

### Def-Use Sets per Statement

| Line | Statement | DEF | USE |
|------|-----------|-----|-----|
| 1 | `nullNotPermitted(data, "data")` | — | data |
| 2 | `total = 0.0` | total | — |
| 3 | `rowCount = data.getRowCount()` | rowCount | data |
| 4 | `r = 0` (init) | r | — |
| 4 | `r < rowCount` (condition) | — | r, rowCount |
| 4 | `r++` (update) | r | r |
| 5 | `n = data.getValue(r, column)` | n | data, r, column |
| 6 | `n != null` | — | n |
| 7 | `total += n.doubleValue()` | total | total, n |
| 8 | `r2 = 0` (init) | r2 | — |
| 8 | `r2 > rowCount` (condition) | — | r2, rowCount |
| 12 | `return total` | — | total |

### All DU-Pairs per Variable

| Variable | DEF (line) | USE (line) | Type |
|----------|-----------|-----------|------|
| data | param | 1 | c-use |
| data | param | 3 | c-use |
| data | param | 5 | c-use |
| total | 2 | 7 (c-use) | c-use |
| total | 2 | 12 | c-use |
| total | 7 | 7 (c-use) | c-use |
| total | 7 | 12 | c-use |
| rowCount | 3 | 4 (p-use) | p-use |
| rowCount | 3 | 8 (p-use) | p-use |
| r | 4-init | 4-cond (p-use) | p-use |
| r | 4-init | 5 | c-use |
| r | 4-update | 4-cond (p-use) | p-use |
| r | 4-update | 5 | c-use |
| n | 5 | 6 (p-use) | p-use |
| n | 5 | 7 | c-use |
| column | param | 5 | c-use |

### DU-Pair Coverage per Test Case

| Test Case | data | total (2→12) | total (2→7) | rowCount | r (4→5) | n (5→6) | n (5→7) | Coverage |
|-----------|------|-------------|------------|---------|---------|---------|---------|---------|
| `calculateColumnTotal_validData_validColumn` | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | All main pairs |
| `calculateColumnTotal_invalidColumn_returnsZero` | ✓ | ✓ | — | ✓ | — | — | — | Partial |
| `calculateColumnTotal_nullData_throwsNPE` | ✓ | — | — | — | — | — | — | Only line 1 |
| `calculateColumnTotal_emptyTable_returnsZero` | ✓ | ✓ | — | ✓ | — | — | — | Partial |

### DU-Pair Coverage Calculation

- Total DU-pairs (reachable, excluding dead code loop): **15**
- DU-pairs covered by test suite: **11**
- **DU-Pair Coverage = 11 / 15 = 73.3%**

> Note: DU-pairs involving the second loop (lines 8–11) are infeasible because `r2 = 0` can never satisfy `r2 > rowCount` when `rowCount ≥ 0`. These 4 pairs are excluded from the denominator.

---

## 2.2 `Range.contains(double value)`

### Source Code Under Analysis

```java
public boolean contains(double value) {
    if (value < this.lower) {   // line 1
        return false;            // line 2
    }
    if (value > this.upper) {   // line 3
        return false;            // line 4
    }
    return (value >= this.lower && value <= this.upper);  // line 5
}
```

### Data-Flow Graph

```
[entry: value, this.lower, this.upper]
     ↓
[1] value < this.lower?  ──Yes──→ [2] return false
     │No
     ↓
[3] value > this.upper?  ──Yes──→ [4] return false
     │No
     ↓
[5] return (value >= lower && value <= upper)
```

### Def-Use Sets per Statement

| Line | Statement | DEF | USE |
|------|-----------|-----|-----|
| 1 | `if (value < this.lower)` | — | value, lower |
| 2 | `return false` | — | — |
| 3 | `if (value > this.upper)` | — | value, upper |
| 4 | `return false` | — | — |
| 5 | `return (value >= lower && value <= upper)` | — | value, lower, upper |

### All DU-Pairs per Variable

| Variable | DEF | USE (line) | Type |
|----------|-----|-----------|------|
| value | param | 1 (p-use) | p-use |
| value | param | 3 (p-use) | p-use |
| value | param | 5 (c-use) | c-use |
| lower | field | 1 (p-use) | p-use |
| lower | field | 5 (c-use) | c-use |
| upper | field | 3 (p-use) | p-use |
| upper | field | 5 (c-use) | c-use |

Total DU-pairs: **7**

### DU-Pair Coverage per Test Case

| Test Case | value→1 | value→3 | value→5 | lower→1 | lower→5 | upper→3 | upper→5 |
|-----------|---------|---------|---------|---------|---------|---------|---------|
| `contains_valueBelowLower` | ✓ (T) | — | — | ✓ | — | — | — |
| `contains_valueAboveUpper` | ✓ (F) | ✓ (T) | — | ✓ | — | ✓ | — |
| `contains_valueInsideRange` | ✓ (F) | ✓ (F) | ✓ | ✓ | ✓ | ✓ | ✓ |
| `contains_valueAtLowerBound` | ✓ (F) | ✓ (F) | ✓ | ✓ | ✓ | ✓ | ✓ |
| `contains_valueAtUpperBound` | ✓ (F) | ✓ (F) | ✓ | ✓ | ✓ | ✓ | ✓ |

### DU-Pair Coverage Calculation

- Total DU-pairs: **7**
- DU-pairs covered: **7**
- **DU-Pair Coverage = 7 / 7 = 100%**

---

# 3 A Detailed Description of the Testing Strategy for the New Unit Tests

> **Note:** This section is a rough draft.

## 3.1 Goal

The goal of the new test cases is to increase coverage of both `DataUtilities` and `Range` to meet or exceed the minimums: **90% statement**, **70% branch**, and **60% condition** coverage.

## 3.2 Coverage Gap Analysis (from Assignment 2 baseline)

After running EclEmma on the Assignment 2 test suite:

| Class | Instruction | Branch | Method |
|-------|------------|--------|--------|
| DataUtilities | 48.0% | 35.9% | 50.0% |
| Range | 14.5% | 13.4% | 26.1% |

Both classes fall well short of the targets. The primary gaps are:
- **DataUtilities**: The overloaded variants `calculateColumnTotal(Values2D, int, int[])`, `calculateRowTotal(Values2D, int, int[])`, `clone(double[][])`, and `equal(double[][], double[][])` are entirely uncovered.
- **Range**: Only 6 of 23 methods were touched by the A2 tests (`contains`, `getCentralValue`, `getLowerBound`, `getUpperBound`, `getLength`, and the constructor). All other methods — `intersects`, `constrain`, `combine`, `combineIgnoringNaN`, `expandToInclude`, `expand`, `shift`, `scale`, `equals`, `hashCode`, `toString`, etc. — have 0% coverage.

## 3.3 Test Plan

| Team Member | Assigned Methods (new tests) |
|-------------|------------------------------|
| Muhammad Zain | `Range.intersects`, `Range.constrain`, `Range.combine`, `Range.equals`, `Range.hashCode`, `Range.toString` |
| Fateh Ali Syed Bukhari | `Range.combineIgnoringNaN`, `Range.expandToInclude`, `Range.expand`, `Range.shift`, `Range.scale` |
| Yazin Abdul Majid | `DataUtilities.equal`, `DataUtilities.clone`, `DataUtilities.calculateColumnTotal(Values2D, int, int[])`, `DataUtilities.calculateRowTotal(Values2D, int, int[])` |

Each team member will:
1. Read the Javadoc specification for their assigned methods.
2. Identify all decision branches in the source code.
3. Write separate `@Test` methods per path/boundary (not one test per method).
4. Run EclEmma after each addition to verify coverage improvement.
5. Peer-review each other's tests for correctness and clarity.

## 3.4 Peer Review Process

After individual completion, each group member will review another member's tests by:
- Verifying that each test has a clear name describing the input scenario and expected output.
- Checking that the assertion matches the Javadoc specification (not the buggy implementation).
- Flagging any test that either cannot fail (always passes trivially) or always fails (testing a bug rather than a requirement).

---

# 4 A High-Level Description of Five Selected Test Cases Designed Using Coverage Information

> **Note:** This section is a rough draft. Final test names and exact coverage improvements will be updated after running the new suite.

The following five test cases were specifically designed by examining EclEmma's red (uncovered) lines in the source code:

### Test Case 1 — `DataUtilities.equal` with both arrays null

**Method:** `DataUtilities.equal(double[][], double[][])`  
**Coverage gap:** This method had 0% instruction and 0% branch coverage.  
**Test:** `equal_bothNull_returnsTrue()` — passes `null, null` and asserts `true`.  
**Improvement:** Covers the `if (a == null) return (b == null)` branch (the `true` path), adding the first covered instructions to this method.

### Test Case 2 — `DataUtilities.clone` with null rows

**Method:** `DataUtilities.clone(double[][])`  
**Coverage gap:** 0% instruction coverage. The `if (source[i] != null)` branch was never entered.  
**Test:** `clone_arrayWithNullRow_clonesCorrectly()` — passes `new double[][] {null, {1.0, 2.0}}` and asserts the result has a null first row.  
**Improvement:** Covers the null-row branch inside the clone loop.

### Test Case 3 — `Range.intersects(double, double)` with overlapping range

**Method:** `Range.intersects(double b0, double b1)`  
**Coverage gap:** 0% instruction, 0% branch.  
**Test:** `intersects_overlappingRange_returnsTrue()` — uses `Range(1, 5)` and checks `intersects(3, 7)`.  
**Improvement:** Covers the `else` branch (`b0 >= lower` path) in `intersects`.

### Test Case 4 — `Range.shift` with zero-crossing prevention

**Method:** `Range.shiftWithNoZeroCrossing(double, double)` (called via `Range.shift`)  
**Coverage gap:** 0% instruction and branch.  
**Test:** `shift_negativeRangeShiftedPositive_clampsAtZero()` — shifts `Range(-3, -1)` by `+5` without zero-crossing; expects `Range(0, 0)`.  
**Improvement:** Covers the `value < 0.0` branch in `shiftWithNoZeroCrossing`.

### Test Case 5 — `Range.combineIgnoringNaN` where one range is NaN

**Method:** `Range.combineIgnoringNaN(Range, Range)`  
**Coverage gap:** 0% instruction and branch.  
**Test:** `combineIgnoringNaN_range1NullRange2NaN_returnsNull()` — passes `null` for `range1` and a NaN Range for `range2`, expects `null`.  
**Improvement:** Covers the `range2 != null && range2.isNaNRange()` inner branch when `range1 == null`.

---

# 5 A Detailed Report of the Coverage Achieved (Assignment 2 Baseline)

## 5.1 Tool Used: EclEmma (JaCoCo)

EclEmma (version bundled with Eclipse 2024-12) was used as the sole coverage tool. It is the recommended tool for this assignment and integrates natively into Eclipse as the "Coverage" launch mode. EclEmma runs using JaCoCo under the hood and provides three metrics directly in the IDE: **Instruction** (equivalent to statement), **Branch**, and **Method** coverage. All measurements below reflect the Assignment 2 test suite running against the Assignment 3 version of the SUT (which contains additional intentional bugs).

> **Note on condition coverage:** EclEmma does not directly report condition coverage. As permitted by the instructions, **Method coverage** is reported as the third metric in place of condition coverage.

---

## 5.2 DataUtilities – Coverage Results

### Instruction Coverage — 48.0%

![DataUtilities Instruction Coverage](<3.1 Coverages/DataUtilities Instruction Coverage.png>)

**Summary:**

| Method | Instruction Coverage | Covered | Missed | Total |
|--------|---------------------|---------|--------|-------|
| `calculateColumnTotal(Values2D, int, int[])` | 0.0% | 0 | 43 | 43 |
| `clone(double[][])` | 0.0% | 0 | 42 | 42 |
| `calculateRowTotal(Values2D, int, int[])` | 0.0% | 0 | 41 | 41 |
| `equal(double[][], double[][])` | 0.0% | 0 | 39 | 39 |
| `calculateColumnTotal(Values2D, int)` | 72.9% | 35 | 13 | 48 |
| `calculateRowTotal(Values2D, int)` | 72.9% | 35 | 13 | 48 |
| `getCumulativePercentages(KeyedValues)` | 85.2% | 69 | 12 | 81 |
| `createNumberArray(double[])` | 100.0% | 26 | 0 | 26 |
| `createNumberArray2D(double[][])` | 100.0% | 25 | 0 | 25 |
| **DataUtilities (class total)** | **48.0%** | **190** | **206** | **396** |

---

### Branch Coverage — 35.9%

![DataUtilities Branch Coverage](<3.1 Coverages/DataUtilities Branch Coverage.png>)

**Summary:**

| Method | Branch Coverage | Covered | Missed | Total |
|--------|----------------|---------|--------|-------|
| `equal(double[][], double[][])` | 0.0% | 0 | 12 | 12 |
| `calculateColumnTotal(Values2D, int, int[])` | 0.0% | 0 | 8 | 8 |
| `calculateRowTotal(Values2D, int, int[])` | 0.0% | 0 | 8 | 8 |
| `clone(double[][])` | 0.0% | 0 | 4 | 4 |
| `calculateColumnTotal(Values2D, int)` | 62.5% | 5 | 3 | 8 |
| `calculateRowTotal(Values2D, int)` | 62.5% | 5 | 3 | 8 |
| `getCumulativePercentages(KeyedValues)` | 75.0% | 9 | 3 | 12 |
| `createNumberArray(double[])` | 100.0% | 2 | 0 | 2 |
| `createNumberArray2D(double[][])` | 100.0% | 2 | 0 | 2 |
| **DataUtilities (class total)** | **35.9%** | **23** | **41** | **64** |

---

### Method Coverage — 50.0%

![DataUtilities Method Coverage](<3.1 Coverages/DataUtilities Method Coverage.png>)

**Summary:**

| Method | Method Coverage | Covered | Missed | Total |
|--------|----------------|---------|--------|-------|
| `calculateColumnTotal(Values2D, int, int[])` | 0.0% | 0 | 1 | 1 |
| `calculateRowTotal(Values2D, int, int[])` | 0.0% | 0 | 1 | 1 |
| `clone(double[][])` | 0.0% | 0 | 1 | 1 |
| `equal(double[][], double[][])` | 0.0% | 0 | 1 | 1 |
| `calculateColumnTotal(Values2D, int)` | 100.0% | 1 | 0 | 1 |
| `calculateRowTotal(Values2D, int)` | 100.0% | 1 | 0 | 1 |
| `createNumberArray(double[])` | 100.0% | 1 | 0 | 1 |
| `createNumberArray2D(double[][])` | 100.0% | 1 | 0 | 1 |
| `getCumulativePercentages(KeyedValues)` | 100.0% | 1 | 0 | 1 |
| **DataUtilities (class total)** | **50.0%** | **5** | **5** | **10** |

---

## 5.3 Range – Coverage Results

### Instruction Coverage — 14.5%

![Range Instruction Coverage](<3.1 Coverages/Range Instruction Coverage.png>)

**Summary:**

| Method | Instruction Coverage | Covered | Missed | Total |
|--------|---------------------|---------|--------|-------|
| `combineIgnoringNaN(Range, Range)` | 0.0% | 0 | 46 | 46 |
| `expand(Range, double, double)` | 0.0% | 0 | 40 | 40 |
| `expandToInclude(Range, double)` | 0.0% | 0 | 34 | 34 |
| `shift(Range, double, boolean)` | 0.0% | 0 | 29 | 29 |
| `hashCode()` | 0.0% | 0 | 28 | 28 |
| `intersects(double, double)` | 0.0% | 0 | 27 | 27 |
| `combine(Range, Range)` | 0.0% | 0 | 26 | 26 |
| `equals(Object)` | 0.0% | 0 | 26 | 26 |
| `constrain(double)` | 0.0% | 0 | 25 | 25 |
| `scale(Range, double)` | 0.0% | 0 | 24 | 24 |
| `shiftWithNoZeroCrossing(double, double)` | 0.0% | 0 | 24 | 24 |
| `toString()` | 0.0% | 0 | 16 | 16 |
| `max(double, double)` | 0.0% | 0 | 14 | 14 |
| `min(double, double)` | 0.0% | 0 | 14 | 14 |
| `isNaNRange()` | 0.0% | 0 | 12 | 12 |
| `intersects(Range)` | 0.0% | 0 | 7 | 7 |
| `shift(Range, double)` | 0.0% | 0 | 5 | 5 |
| `getLength()` | 36.4% | 12 | 21 | 33 |
| `getLowerBound()` | 30.0% | 9 | 21 | 30 |
| `getUpperBound()` | 30.0% | 9 | 21 | 30 |
| `Range(double, double)` (constructor) | 40.6% | 13 | 19 | 32 |
| `contains(double)` | 100.0% | 28 | 0 | 28 |
| `getCentralValue()` | 100.0% | 10 | 0 | 10 |
| **Range (class total)** | **14.5%** | **81** | **479** | **560** |

---

### Branch Coverage — 13.4%

![Range Branch Coverage](<3.1 Coverages/Range Brach Coverage.png>)

**Summary:**

| Method | Branch Coverage | Covered | Missed | Total |
|--------|----------------|---------|--------|-------|
| `combineIgnoringNaN(Range, Range)` | 0.0% | 0 | 14 | 14 |
| `intersects(double, double)` | 0.0% | 0 | 8 | 8 |
| `expandToInclude(Range, double)` | 0.0% | 0 | 6 | 6 |
| `constrain(double)` | 0.0% | 0 | 6 | 6 |
| `equals(Object)` | 0.0% | 0 | 6 | 6 |
| `combine(Range, Range)` | 0.0% | 0 | 4 | 4 |
| `max(double, double)` | 0.0% | 0 | 4 | 4 |
| `min(double, double)` | 0.0% | 0 | 4 | 4 |
| `shiftWithNoZeroCrossing(double, double)` | 0.0% | 0 | 4 | 4 |
| `isNaNRange()` | 0.0% | 0 | 4 | 4 |
| `expand(Range, double, double)` | 0.0% | 0 | 2 | 2 |
| `scale(Range, double)` | 0.0% | 0 | 2 | 2 |
| `shift(Range, double, boolean)` | 0.0% | 0 | 2 | 2 |
| `Range(double, double)` (constructor) | 50.0% | 1 | 1 | 2 |
| `getLength()` | 50.0% | 1 | 1 | 2 |
| `getLowerBound()` | 50.0% | 1 | 1 | 2 |
| `getUpperBound()` | 50.0% | 1 | 1 | 2 |
| `contains(double)` | 87.5% | 7 | 1 | 8 |
| **Range (class total)** | **13.4%** | **11** | **71** | **82** |

---

### Method Coverage — 26.1%

![Range Method Coverage](<3.1 Coverages/Range Method Coverage.png>)

**Summary:**

| Method | Method Coverage | Covered | Missed | Total |
|--------|----------------|---------|--------|-------|
| `combine(Range, Range)` | 0.0% | 0 | 1 | 1 |
| `combineIgnoringNaN(Range, Range)` | 0.0% | 0 | 1 | 1 |
| `expand(Range, double, double)` | 0.0% | 0 | 1 | 1 |
| `expandToInclude(Range, double)` | 0.0% | 0 | 1 | 1 |
| `max(double, double)` | 0.0% | 0 | 1 | 1 |
| `min(double, double)` | 0.0% | 0 | 1 | 1 |
| `scale(Range, double)` | 0.0% | 0 | 1 | 1 |
| `shift(Range, double)` | 0.0% | 0 | 1 | 1 |
| `shift(Range, double, boolean)` | 0.0% | 0 | 1 | 1 |
| `shiftWithNoZeroCrossing(double, double)` | 0.0% | 0 | 1 | 1 |
| `constrain(double)` | 0.0% | 0 | 1 | 1 |
| `equals(Object)` | 0.0% | 0 | 1 | 1 |
| `hashCode()` | 0.0% | 0 | 1 | 1 |
| `intersects(double, double)` | 0.0% | 0 | 1 | 1 |
| `intersects(Range)` | 0.0% | 0 | 1 | 1 |
| `isNaNRange()` | 0.0% | 0 | 1 | 1 |
| `toString()` | 0.0% | 0 | 1 | 1 |
| `Range(double, double)` (constructor) | 100.0% | 1 | 0 | 1 |
| `contains(double)` | 100.0% | 1 | 0 | 1 |
| `getCentralValue()` | 100.0% | 1 | 0 | 1 |
| `getLength()` | 100.0% | 1 | 0 | 1 |
| `getLowerBound()` | 100.0% | 1 | 0 | 1 |
| `getUpperBound()` | 100.0% | 1 | 0 | 1 |
| **Range (class total)** | **26.1%** | **6** | **17** | **23** |

---

## 5.4 Coverage Summary Table

| Class | Instruction Coverage | Branch Coverage | Method Coverage |
|-------|---------------------|----------------|----------------|
| `DataUtilities` | **48.0%** (190/396) | **35.9%** (23/64) | **50.0%** (5/10) |
| `Range` | **14.5%** (81/560) | **13.4%** (11/82) | **26.1%** (6/23) |

---

## 5.5 Analysis

### Why instruction coverage is higher than branch coverage

Instruction coverage measures whether each bytecode instruction was executed at least once. Branch coverage additionally requires that every decision point (e.g., `if`, loop condition) produces both a `true` and a `false` outcome. A single test can cover many instructions in one branch of an `if` statement, yet leave the opposite branch entirely unexplored. For example, in `calculateColumnTotal(Values2D, int)`, the test for a valid column executes the loop body (many instructions), raising instruction coverage, but the `if (n != null)` branch's `false` path is never triggered, depressing branch coverage. As a result, instruction coverage will always be ≥ branch coverage for any given method, and the gap widens when methods contain many instructions inside branches that are not both tested.

### Why branch coverage is so low

Branch coverage is low primarily because Assignment 2 used a **black-box, requirements-based approach** targeting only five methods each for `DataUtilities` and `Range`. The overloaded and utility methods (`equal`, `clone`, `calculateColumnTotal(…, int[])`, and 17 out of 23 methods in `Range`) received zero tests. Each unexercised method contributes 0% to the branch total, dragging the overall percentage down significantly. Additionally, even the methods that were tested contain dead-code branches (e.g., the `r2 > rowCount` loop in `calculateColumnTotal`) which are impossible to cover regardless.

### Why many classes and methods show 0%

Because the test suite from Assignment 2 was scoped to **10 specific methods** across the two SUT classes, and EclEmma is run against the full compiled project (all of `org.jfree`), every class that was not the target of any test method shows 0% across all metrics. Within `DataUtilities`, four of nine public methods were never called. Within `Range`, 17 of 23 methods were never entered. This is expected at the Assignment 2 baseline — the coverage improvement task (Section 3.3) is designed to close these gaps.

---

# 6 Pros and Cons of Coverage Tools Used and Metrics Reported

## 6.1 Tool Evaluated: EclEmma (JaCoCo)

EclEmma is an Eclipse plug-in that integrates JaCoCo as its coverage engine. It was the only tool we actively used for this assignment. We also evaluated two alternatives briefly, documented below.

### Pros of EclEmma

| Pro | Detail |
|-----|--------|
| **Seamless Eclipse integration** | Runs as a one-click "Coverage As → JUnit Test" launch mode with no extra configuration. No build-script changes needed. |
| **Color-coded source view** | Red/yellow/green line highlighting in the editor immediately shows which lines, branches (indicated by diamonds), and methods are covered — making it easy to identify gaps. |
| **No conflicts with Mockito/JUnit 5** | EclEmma worked correctly with our JUnit 5 (Jupiter) tests and Mockito-based mocks without any modification. |
| **Multiple metrics in one run** | A single run produces instruction, branch, method, line, and complexity metrics simultaneously, visible in the Coverage view. |
| **Free and actively maintained** | Part of the standard Eclipse distribution; no license required. |
| **Accurate bytecode-level instrumentation** | JaCoCo instruments bytecode rather than source, which avoids false negatives from compiler optimisations. |

### Cons of EclEmma

| Con | Detail |
|-----|--------|
| **No condition (MC/DC) coverage** | EclEmma reports branch coverage but cannot distinguish whether individual Boolean sub-expressions within compound conditions are independently tested. Condition coverage must be assessed manually or with a different tool. |
| **Instruction ≠ statement (in practice)** | EclEmma's "instruction" metric operates at the bytecode level. A single Java source line can correspond to multiple bytecode instructions, so "instruction" coverage is not exactly the same as source-level statement coverage, though it is a good approximation. |
| **Aggregates entire project** | The Coverage view shows all packages in the project, not just the two under test. This requires manual filtering to isolate `DataUtilities` and `Range` results, which is mildly cumbersome. |
| **Cannot cover infeasible branches** | The dead-code loop in `calculateColumnTotal` (`r2 > rowCount` starting at 0) is flagged as uncovered even though it is logically unreachable. EclEmma provides no mechanism to mark branches as infeasible. |
| **No historical comparison** | EclEmma does not retain previous run results for trend comparison without exporting reports manually. |

### Tools Considered but Not Used

| Tool | Reason Not Used |
|------|----------------|
| **CodeCover** | No longer actively maintained; Eclipse update site is offline. Could not install. |
| **Cobertura** | Requires Ant/Maven build pipeline; incompatible with our Eclipse-only project setup without significant reconfiguration. |

---

## 6.2 Metric Selection Rationale

We selected **instruction**, **branch**, and **method** coverage as our three reported metrics. The assignment specifies statement, branch, and condition — and since EclEmma does not support condition coverage, we substituted method coverage as instructed.

| Metric | Why it was chosen | Limitation |
|--------|-------------------|------------|
| **Instruction (Statement)** | Most fundamental adequacy criterion; ensures every executable line is visited. Widely supported and easy to interpret. | Does not guarantee both paths through a branch are exercised. |
| **Branch** | Directly measures decision coverage; more demanding than statement coverage. Ensures both `true` and `false` outcomes of each `if`/`loop` are tested. | Does not cover compound conditions independently (that requires condition/MC/DC coverage). |
| **Method** | Simple, high-level indicator of which API entry points have any tests at all. Useful for identifying completely untested methods quickly. | A method can be "covered" by a single trivial test without meaningfully exercising its logic. |

---

# 7 A Comparison of Requirements-Based and Coverage-Based Test Generation

> **Note:** This section is a rough draft.

**Requirements-based test generation** (used in Assignment 2) derives test cases entirely from the specification — in our case, the Javadoc API documentation. The tester treats the code as a black box. The key advantage is that tests are grounded in the intended behaviour, not the implementation; defects introduced by the developer (including dead code or incorrect branches) do not bias test design. The main disadvantage is that without seeing the code, a tester may miss entire execution paths that are not implied by the Javadoc, leading to low structural coverage.

**Coverage-based test generation** (used in this assignment) inspects the source code to identify uncovered statements, branches, or paths and designs test cases to exercise them. The advantage is systematic completeness — no branch is left dark after enough iterations. The disadvantage is that tests written solely to achieve coverage metrics may lose sight of the specification: a test designed to cover a buggy branch is not a correct test, and chasing 100% coverage can lead to meaningless tests that inflate metrics without validating real requirements.

In practice, a mature test strategy combines both approaches: requirements-based testing as the primary source of test oracles, augmented by coverage analysis to identify gaps in structural exercise. Our workflow in this assignment followed exactly that pattern — starting with the A2 black-box tests, measuring coverage, then adding new tests guided by coverage gaps while still deriving assertions from the Javadoc.

---

# 8 A Discussion on How the Team Work and Effort Was Divided and Managed

> **Note:** This section is a rough draft.

The work was divided into three roughly equal streams aligned with each team member's experience from Assignment 2:

- **Muhammad Zain** took responsibility for setting up the EclEmma coverage runs, capturing and labelling all six baseline screenshots, filling in the coverage tables in the report, and writing new tests for the `Range` utility methods (`intersects`, `constrain`, `combine`, `equals`, `hashCode`, `toString`).
- **Fateh Ali Syed Bukhari** was responsible for the manual data-flow coverage calculations (Section 2), including drawing the data-flow graph and DU-pair tables for `calculateColumnTotal`, and writing new tests for the advanced `Range` factory methods (`combineIgnoringNaN`, `expandToInclude`, `expand`, `shift`, `scale`).
- **Yazin Abdul Majid** was responsible for the new `DataUtilities` tests (`equal`, `clone`, overloaded `calculateColumnTotal`, overloaded `calculateRowTotal`) and led the peer-review process by reviewing all new test code for correctness and Javadoc alignment.

Team coordination was managed through WhatsApp group messaging for day-to-day updates, and a shared Google Doc was used to draft and review the report before committing to the Markdown file. Each member reviewed the other two members' code before submission.

---

# 9 Difficulties Encountered, Challenges Overcome, and Lessons Learned

> **Note:** This section is a rough draft.

**EclEmma metric limitations:** Our first challenge was that EclEmma does not support condition (MC/DC) coverage. After briefly investigating CodeCover (offline) and Cobertura (requires Ant/Maven build), we accepted the assignment's suggestion to substitute method coverage as the third metric.

**Intentional defects and dead code:** Several methods in both `DataUtilities` and `Range` contain intentionally injected bugs. The most notable was the `r2 > rowCount` loop in `calculateColumnTotal` — a dead-code loop that EclEmma correctly marks as uncovered. Writing a test to cover it is impossible by design, which taught us the concept of **infeasible paths** and how to communicate them in a report rather than forcing artificial coverage.

**Scoping the Coverage view:** EclEmma shows all packages in the project. Filtering down to just `org.jfree.data.DataUtilities` and `org.jfree.data.Range` required manually collapsing other packages — a minor but time-consuming inconvenience.

**Lesson learned:** Coverage metrics are necessary but not sufficient for test quality. A test that covers a dead branch or a buggy branch counts as "covered" but provides no assurance of correctness. The oracle (what you assert) matters as much as the path you traverse.

---

# 10 Comments/Feedback on the Lab Itself

> **Note:** This section is a rough draft.

The lab was well-structured and a natural extension of Assignment 2. Having the same SUT and test files allowed us to focus on the new concepts (coverage metrics and data-flow analysis) without re-learning the codebase.

One suggestion: the instructions recommend EclEmma as the "recommended tool" but do not clarify upfront that it does not support condition coverage. Discovering this after setting up the tool and trying to measure it cost some time. A brief note in the instructions saying "EclEmma does not support MC/DC or condition coverage — substitute method coverage" would streamline the workflow.

The manual DU-pair exercise (Section 2) was the most educational part of the assignment. Tracing through `calculateColumnTotal` by hand revealed the dead-code loop clearly, which would have been easy to overlook when just reading the code quickly.

Overall, this lab effectively demonstrates why a test suite that looks "complete" from a requirements perspective can still leave large portions of the code untested — and provides concrete tools to measure and improve that situation.
