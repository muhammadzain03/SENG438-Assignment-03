package org.jfree.data;

import static org.junit.jupiter.api.Assertions.*;

import org.jfree.data.Range;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Black-box unit tests for org.jfree.data.Range.
 * Covers 5 methods: getLowerBound, getUpperBound, getCentralValue, getLength, contains.
 * Test design: Equivalence Class Partitioning (ECP) and Boundary Value Analysis (BVA).
 * 
 * @author Group 08 - SENG 438
 */
public class RangeTest {

    private Range symmetricRange;  // -1 to 1, used in @BeforeEach

    @BeforeEach
    void setUp() {
        symmetricRange = new Range(-1, 1);
    }

    // ==================== getLowerBound() – 5 methods chosen: 1/5 ====================

    // PASS
    /** ECP: nominal range with negative lower bound. */
    @Test
    void getLowerBound_NegativeLower_ReturnsLower() {
        assertEquals(-1.0, symmetricRange.getLowerBound(), 1e-9,
                "Range(-1, 1) lower bound should be -1");
    }

    // FAIL
    /** BVA: lower bound at zero. */
    @Test
    void getLowerBound_ZeroLower_ReturnsZero() {
        Range r = new Range(0, 5);
        assertEquals(0.0, r.getLowerBound(), 1e-9, "Range(0, 5) lower bound should be 0");
    }

    // PASS
    /** BVA: single-point range (lower == upper). */
    @Test
    void getLowerBound_SinglePointRange_ReturnsThatValue() {
        Range r = new Range(7.5, 7.5);
        assertEquals(7.5, r.getLowerBound(), 1e-9, "Range(7.5, 7.5) lower bound should be 7.5");
    }

    // PASS
    /** ECP: positive lower bound. */
    @Test
    void getLowerBound_PositiveLower_ReturnsLower() {
        Range r = new Range(10, 20);
        assertEquals(10.0, r.getLowerBound(), 1e-9, "Range(10, 20) lower bound should be 10");
    }
    
    // PASS
    /** ECP: positive lower bound (alternative test case). */
    @Test
    void getLowerBound_PositiveRange_ReturnsOne() {
        Range r = new Range(1, 3);
        assertEquals(1.0, r.getLowerBound(), 1e-9, "Range(1, 3) lower bound should be 1");
    }

    // ==================== getUpperBound() – 2/5 ====================

    // PASS
    /** ECP: nominal range with positive upper bound. */
    @Test
    void getUpperBound_PositiveUpper_ReturnsUpper() {
        assertEquals(1.0, symmetricRange.getUpperBound(), 1e-9,
                "Range(-1, 1) upper bound should be 1");
    }

    // FAIL
    /** BVA: upper bound at zero. */
    @Test
    void getUpperBound_ZeroUpper_ReturnsZero() {
        Range r = new Range(-5, 0);
        assertEquals(0.0, r.getUpperBound(), 1e-9, "Range(-5, 0) upper bound should be 0");
    }

    // PASS
    /** BVA: single-point range (upper == lower). */
    @Test
    void getUpperBound_SinglePointRange_ReturnsThatValue() {
        Range r = new Range(-3.0, -3.0);
        assertEquals(-3.0, r.getUpperBound(), 1e-9, "Range(-3, -3) upper bound should be -3");
    }

    // PASS
    /** ECP: negative upper bound (range entirely negative). */
    @Test
    void getUpperBound_NegativeUpper_ReturnsUpper() {
        Range r = new Range(-20, -10);
        assertEquals(-10.0, r.getUpperBound(), 1e-9, "Range(-20, -10) upper bound should be -10");
    }
    
    // PASS
    /** ECP: positive range upper bound (alternative test case). */
    @Test
    void getUpperBound_PositiveRange_ReturnsThree() {
        Range r = new Range(1, 3);
        assertEquals(3.0, r.getUpperBound(), 1e-9, "Range(1, 3) upper bound should be 3");
    }

    // ==================== getCentralValue() – 3/5 ====================

    // PASS
    /** ECP / BVA: symmetric range, central value at zero. */
    @Test
    void getCentralValue_SymmetricRange_ReturnsZero() {
        assertEquals(0.0, symmetricRange.getCentralValue(), 1e-9,
                "Range(-1, 1) central value should be 0");
    }

    // FAIL
    /** ECP: asymmetric positive range. */
    @Test
    void getCentralValue_AsymmetricPositiveRange_ReturnsMidpoint() {
        Range r = new Range(0, 10);
        assertEquals(5.0, r.getCentralValue(), 1e-9, "Range(0, 10) central value should be 5");
    }

    // PASS
    /** ECP: asymmetric negative range. */
    @Test
    void getCentralValue_AsymmetricNegativeRange_ReturnsMidpoint() {
        Range r = new Range(-10, -2);
        assertEquals(-6.0, r.getCentralValue(), 1e-9, "Range(-10, -2) central value should be -6");
    }

    // PASS
    /** BVA: single-point range; central value is the only value. */
    @Test
    void getCentralValue_SinglePointRange_ReturnsThatValue() {
        Range r = new Range(4.0, 4.0);
        assertEquals(4.0, r.getCentralValue(), 1e-9, "Range(4, 4) central value should be 4");
    }

    // PASS
    /** BVA: range spanning zero (mixed signs). */
    @Test
    void getCentralValue_RangeSpanningZero_ReturnsMidpoint() {
        Range r = new Range(-4, 6);
        assertEquals(1.0, r.getCentralValue(), 1e-9, "Range(-4, 6) central value should be 1");
    }
    
    // PASS
    /** ECP: positive range (alternative test case). */
    @Test
    void getCentralValue_PositiveRange_ReturnsTwo() {
        Range r = new Range(1, 3);
        assertEquals(2.0, r.getCentralValue(), 1e-9, "Range(1, 3) central value should be 2");
    }
    
    // PASS
    /** ECP: negative to zero range (alternative test case). */
    @Test
    void getCentralValue_NegativeToZeroRange_ReturnsMinusFive() {
        Range r = new Range(-10, 0);
        assertEquals(-5.0, r.getCentralValue(), 1e-9, "Range(-10, 0) central value should be -5");
    }

    // ==================== getLength() – 4/5 ====================

    // PASS
    /** ECP: nominal positive-length range. */
    @Test
    void getLength_PositiveRange_ReturnsLength() {
        assertEquals(2.0, symmetricRange.getLength(), 1e-9,
                "Range(-1, 1) length should be 2");
    }

    // PASS
    /** BVA: zero-length range (single point). */
    @Test
    void getLength_ZeroLengthRange_ReturnsZero() {
        Range r = new Range(5.0, 5.0);
        assertEquals(0.0, r.getLength(), 1e-9, "Range(5, 5) length should be 0");
    }

    // FAIL
    /** ECP: larger positive range. */
    @Test
    void getLength_LargeRange_ReturnsCorrectLength() {
        Range r = new Range(0, 100);
        assertEquals(100.0, r.getLength(), 1e-9, "Range(0, 100) length should be 100");
    }

    // PASS
    /** ECP: negative range (length still positive as upper - lower). */
    @Test
    void getLength_NegativeRange_ReturnsPositiveLength() {
        Range r = new Range(-8, -2);
        assertEquals(6.0, r.getLength(), 1e-9, "Range(-8, -2) length should be 6");
    }
    
    // PASS
    /** ECP: mixed sign range (alternative test case). */
    @Test
    void getLength_MixedSignRange_ReturnsTen() {
        Range r = new Range(-3, 7);
        assertEquals(10.0, r.getLength(), 1e-9, "Range(-3, 7) length should be 10");
    }

    // ==================== contains(double value) – 5/5 ====================

    // PASS
    /** ECP: value strictly inside range. */
    @Test
    void contains_ValueInsideRange_ReturnsTrue() {
        assertTrue(symmetricRange.contains(0),
                "Range(-1, 1) should contain 0");
    }

    // PASS
    /** BVA: value at lower bound (inclusive). */
    @Test
    void contains_ValueAtLowerBound_ReturnsTrue() {
        assertTrue(symmetricRange.contains(-1),
                "Range(-1, 1) should contain -1 (at lower bound)");
    }

    // PASS
    /** BVA: value at upper bound (inclusive). */
    @Test
    void contains_ValueAtUpperBound_ReturnsTrue() {
        assertTrue(symmetricRange.contains(1),
                "Range(-1, 1) should contain 1 (at upper bound)");
    }

    // PASS
    /** BVA: value just below lower bound. */
    @Test
    void contains_ValueJustBelowLowerBound_ReturnsFalse() {
        assertFalse(symmetricRange.contains(-1.0001),
                "Range(-1, 1) should not contain -1.0001");
    }

    // PASS
    /** BVA: value just above upper bound. */
    @Test
    void contains_ValueJustAboveUpperBound_ReturnsFalse() {
        assertFalse(symmetricRange.contains(1.0001),
                "Range(-1, 1) should not contain 1.0001");
    }

    // PASS
    /** ECP: value well below range. */
    @Test
    void contains_ValueWellBelowRange_ReturnsFalse() {
        assertFalse(symmetricRange.contains(-100),
                "Range(-1, 1) should not contain -100");
    }

    // PASS
    /** ECP: value well above range. */
    @Test
    void contains_ValueWellAboveRange_ReturnsFalse() {
        assertFalse(symmetricRange.contains(100),
                "Range(-1, 1) should not contain 100");
    }

    // PASS
    /** BVA: single-point range contains only that value. */
    @Test
    void contains_SinglePointRange_ContainsOnlyThatValue() {
        Range r = new Range(3.0, 3.0);
        assertTrue(r.contains(3.0), "Range(3, 3) should contain 3");
        assertFalse(r.contains(2.99), "Range(3, 3) should not contain 2.99");
        assertFalse(r.contains(3.01), "Range(3, 3) should not contain 3.01");
    }
    
    // PASS
    /** ECP: value below range (alternative test case). */
    @Test
    void contains_ValueBelowRange_ReturnsFalse() {
        assertFalse(symmetricRange.contains(-2),
                "Range(-1, 1) should not contain -2");
    }
    
    // PASS
    /** ECP: value above range (alternative test case). */
    @Test
    void contains_ValueAboveRange_ReturnsFalse() {
        assertFalse(symmetricRange.contains(2),
                "Range(-1, 1) should not contain 2");
    }
    
    // PASS
    /** ECP: NaN value should not be contained. */
    @Test
    void contains_NaNValue_ReturnsFalse() {
        assertFalse(symmetricRange.contains(Double.NaN),
                "Range(-1, 1) should not contain NaN");
    }
}
