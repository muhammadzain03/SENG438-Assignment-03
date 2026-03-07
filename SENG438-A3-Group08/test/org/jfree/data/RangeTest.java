package org.jfree.data;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
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
    @Test
    void constrain_ValueInsideRange_ReturnsSameValue() {
        Range r = new Range(0.0, 10.0);
        assertEquals(5.0, r.constrain(5.0), 1e-9,
            "constrain(5.0) on Range(0,10) should return 5.0");
    }

    @Test
    void constrain_ValueBelowLower_ReturnsLower() {
        Range r = new Range(0.0, 10.0);
        assertEquals(0.0, r.constrain(-5.0), 1e-9,
            "constrain(-5.0) on Range(0,10) should return lower bound 0.0");
    }

    @Test
    void constrain_ValueAboveUpper_ReturnsUpper() {
        Range r = new Range(0.0, 10.0);
        assertEquals(10.0, r.constrain(15.0), 1e-9,
            "constrain(15.0) on Range(0,10) should return upper bound 10.0");
    }

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

    // ==================== Coverage Tests ====================

    // ==================== 2.1 Range(double, double) constructor ====================

    @Test
    void constructor_LowerGreaterThanUpper_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Range(5.0, 0.0),
            "Range(5.0, 0.0) should throw IllegalArgumentException when lower > upper");
    }

    @Test
    void constructor_LowerGreaterThanUpper_NegativeBounds_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Range(1.0, -1.0),
            "Range(1.0, -1.0) should throw IllegalArgumentException when lower > upper");
    }

    // ==================== 2.2 constrain(double) ====================



    // ==================== 2.2 intersects(double, double) ====================

    // b0 <= lower AND b1 > lower → true
    @Test
    void intersects_b0AtLowerAndB1AboveLower_ReturnsTrue() {
        Range r = new Range(2.0, 8.0);
        assertTrue(r.intersects(2.0, 5.0),
            "intersects(2.0, 5.0) on Range(2,8): b0=lower, b1>lower → true");
    }

    // b0 < lower AND b1 > lower → true (spans lower)
    @Test
    void intersects_b0BelowLowerAndB1AboveLower_ReturnsTrue() {
        Range r = new Range(2.0, 8.0);
        assertTrue(r.intersects(0.0, 5.0),
            "intersects(0.0, 5.0) on Range(2,8): b0<lower, b1>lower → true");
    }

    // b0 <= lower AND b1 <= lower → false
    @Test
    void intersects_b0BelowLowerAndB1AtLower_ReturnsFalse() {
        Range r = new Range(2.0, 8.0);
        assertFalse(r.intersects(0.0, 2.0),
            "intersects(0.0, 2.0) on Range(2,8): b1 not > lower → false");
    }

    // b0 > lower, b0 < upper, b1 >= b0 → true (else branch, inside)
    @Test
    void intersects_b0AboveLowerAndInsideRange_ReturnsTrue() {
        Range r = new Range(2.0, 8.0);
        assertTrue(r.intersects(4.0, 6.0),
            "intersects(4.0, 6.0) on Range(2,8): b0 inside range → true");
    }

    // b0 >= upper → false (else branch, b0 not < upper)
    @Test
    void intersects_b0AtOrAboveUpper_ReturnsFalse() {
        Range r = new Range(2.0, 8.0);
        assertFalse(r.intersects(8.0, 10.0),
            "intersects(8.0, 10.0) on Range(2,8): b0 >= upper → false");
    }

    // ==================== 2.2 intersects(Range) ====================

    @Test
    void intersects_OverlappingRange_ReturnsTrue() {
        Range r = new Range(2.0, 8.0);
        assertTrue(r.intersects(new Range(5.0, 12.0)),
            "Range(2,8).intersects(Range(5,12)) → true");
    }

    @Test
    void intersects_NonOverlappingRange_ReturnsFalse() {
        Range r = new Range(2.0, 8.0);
        assertFalse(r.intersects(new Range(10.0, 15.0)),
            "Range(2,8).intersects(Range(10,15)) → false");
    }

    // ==================== 2.3 combine(Range, Range) ====================

    @Test
    void combine_BothNull_ReturnsNull() {
        assertNull(Range.combine(null, null),
            "combine(null, null) should return null");
    }

    @Test
    void combine_Range1NullRange2NonNull_ReturnsRange2() {
        Range r2 = new Range(3.0, 7.0);
        assertEquals(r2, Range.combine(null, r2),
            "combine(null, Range(3,7)) should return Range(3,7)");
    }

    @Test
    void combine_Range1NonNullRange2Null_ReturnsRange1() {
        Range r1 = new Range(1.0, 5.0);
        assertEquals(r1, Range.combine(r1, null),
            "combine(Range(1,5), null) should return Range(1,5)");
    }

    @Test
    void combine_TwoOverlappingRanges_ReturnsSpanningRange() {
        Range result = Range.combine(new Range(1.0, 5.0), new Range(3.0, 8.0));
        assertEquals(new Range(1.0, 8.0), result,
            "combine(Range(1,5), Range(3,8)) should return Range(1,8)");
    }

    @Test
    void combine_TwoDisjointRanges_ReturnsSpanningRange() {
        Range result = Range.combine(new Range(1.0, 3.0), new Range(7.0, 10.0));
        assertEquals(new Range(1.0, 10.0), result,
            "combine(Range(1,3), Range(7,10)) should return Range(1,10)");
    }

    // ==================== 2.3 combineIgnoringNaN(Range, Range) ====================

    // range1==null, range2 is NaN-range → null
    @Test
    void combineIgnoringNaN_Range1NullRange2IsNaN_ReturnsNull() {
        Range nanRange = new Range(Double.NaN, Double.NaN);
        assertNull(Range.combineIgnoringNaN(null, nanRange),
            "combineIgnoringNaN(null, NaN-range) should return null");
    }

    // range1==null, range2 is normal → return range2
    @Test
    void combineIgnoringNaN_Range1NullRange2Normal_ReturnsRange2() {
        Range r2 = new Range(1.0, 5.0);
        assertEquals(r2, Range.combineIgnoringNaN(null, r2),
            "combineIgnoringNaN(null, Range(1,5)) should return Range(1,5)");
    }

    // range2==null, range1 is NaN-range → null
    @Test
    void combineIgnoringNaN_Range2NullRange1IsNaN_ReturnsNull() {
        Range nanRange = new Range(Double.NaN, Double.NaN);
        assertNull(Range.combineIgnoringNaN(nanRange, null),
            "combineIgnoringNaN(NaN-range, null) should return null");
    }

    // range2==null, range1 is normal → return range1
    @Test
    void combineIgnoringNaN_Range2NullRange1Normal_ReturnsRange1() {
        Range r1 = new Range(1.0, 5.0);
        assertEquals(r1, Range.combineIgnoringNaN(r1, null),
            "combineIgnoringNaN(Range(1,5), null) should return Range(1,5)");
    }

    // one NaN range, one normal → return spanning range dropping NaN
    @Test
    void combineIgnoringNaN_OneNaNOneNormal_ReturnsNormalRange() {
        Range normal = new Range(1.0, 3.0);
        Range nanRange = new Range(Double.NaN, Double.NaN);
        Range result = Range.combineIgnoringNaN(nanRange, normal);
        assertEquals(new Range(1.0, 3.0), result,
            "combineIgnoringNaN(NaN-range, Range(1,3)) should return Range(1,3)");
    }

    // both NaN ranges → l and u both NaN → null
    @Test
    void combineIgnoringNaN_BothNaNRanges_ReturnsNull() {
        Range nan1 = new Range(Double.NaN, Double.NaN);
        Range nan2 = new Range(Double.NaN, Double.NaN);
        assertNull(Range.combineIgnoringNaN(nan1, nan2),
            "combineIgnoringNaN(NaN-range, NaN-range) should return null");
    }

    // both normal ranges → spanning range
    @Test
    void combineIgnoringNaN_BothNormalRanges_ReturnsSpanningRange() {
        Range result = Range.combineIgnoringNaN(new Range(1.0, 3.0), new Range(2.0, 5.0));
        assertEquals(new Range(1.0, 5.0), result,
            "combineIgnoringNaN(Range(1,3), Range(2,5)) should return Range(1,5)");
    }

    // normal range + NaN-range → min(nonNaN, NaN) and max(nonNaN, NaN)
    // Hits the "if (Double.isNaN(d2)) return d1" branch in private min and max helpers
    @Test
    void combineIgnoringNaN_NormalRangeThenNaNRange_ReturnsNormalRange() {
        Range normal = new Range(2.0, 5.0);
        Range nanRange = new Range(Double.NaN, Double.NaN);
        Range result = Range.combineIgnoringNaN(normal, nanRange);
        assertEquals(new Range(2.0, 5.0), result,
            "combineIgnoringNaN(Range(2,5), NaN-range): min(2,NaN)=2, max(5,NaN)=5 → Range(2,5)");
    }

    // range with NaN lower + range with NaN upper → exercises mixed NaN bounds across helpers
    @Test
    void combineIgnoringNaN_OneNaNBoundEach_UsesMinMaxHelpers() {
        // range1.lower=NaN, range2.upper=NaN: min(NaN,2)=2 via branch-1, max(5,NaN)=5 via branch-2
        Range r1 = new Range(Double.NaN, 5.0);
        Range r2 = new Range(2.0, Double.NaN);
        Range result = Range.combineIgnoringNaN(r1, r2);
        assertEquals(new Range(2.0, 5.0), result,
            "combineIgnoringNaN(Range(NaN,5), Range(2,NaN)): min(NaN,2)=2, max(5,NaN)=5 → Range(2,5)");
    }

    // ==================== 2.3 expandToInclude(Range, double) ====================

    @Test
    void expandToInclude_NullRange_ReturnsPointRange() {
        Range result = Range.expandToInclude(null, 4.0);
        assertEquals(new Range(4.0, 4.0), result,
            "expandToInclude(null, 4.0) should return Range(4.0, 4.0)");
    }

    @Test
    void expandToInclude_ValueBelowLower_ExpandsLower() {
        Range result = Range.expandToInclude(new Range(3.0, 8.0), 1.0);
        assertEquals(new Range(1.0, 8.0), result,
            "expandToInclude(Range(3,8), 1.0): value < lower → Range(1,8)");
    }

    @Test
    void expandToInclude_ValueAboveUpper_ExpandsUpper() {
        Range result = Range.expandToInclude(new Range(3.0, 8.0), 10.0);
        assertEquals(new Range(3.0, 10.0), result,
            "expandToInclude(Range(3,8), 10.0): value > upper → Range(3,10)");
    }

    @Test
    void expandToInclude_ValueInsideRange_ReturnsSameRange() {
        Range original = new Range(3.0, 8.0);
        Range result = Range.expandToInclude(original, 5.0);
        assertSame(original, result,
            "expandToInclude(Range(3,8), 5.0): value inside → same Range instance");
    }

    // ==================== 2.3 expand(Range, double, double) ====================

    @Test
    void expand_NormalMargins_ReturnsExpandedRange() {
        // Range(2,4), length=2: lower=2-2*0.1=1.8, upper=4+2*0.1=4.2
        Range result = Range.expand(new Range(2.0, 4.0), 0.1, 0.1);
        assertEquals(new Range(1.8, 4.2), result,
            "expand(Range(2,4), 0.1, 0.1) should return Range(1.8, 4.2)");
    }

    @Test
    void expand_LargeMarginsCollapseToMidpoint_ReturnsCollapsedRange() {
        // Range(0,10), length=10: lower=0-10*0=0, upper=10+10*(-2)=-10
        // lower(0) > upper(-10) → collapse: lower=upper=(0/2+(-10)/2)=-5
        Range result = Range.expand(new Range(0.0, 10.0), 0.0, -2.0);
        assertEquals(new Range(-5.0, -5.0), result,
            "expand with collapsing margins should collapse to midpoint Range(-5,-5)");
    }

    // ==================== 2.4 shift(Range, double) and shift(Range, double, boolean) ====================

    // shift(base, delta) — delegates to shift(base, delta, false)
    @Test
    void shift_PositiveDelta_ShiftsRange() {
        Range result = Range.shift(new Range(1.0, 5.0), 3.0);
        assertEquals(new Range(4.0, 8.0), result,
            "shift(Range(1,5), 3.0) should return Range(4,8)");
    }

    @Test
    void shift_NegativeDelta_ShiftsRange() {
        Range result = Range.shift(new Range(3.0, 7.0), -2.0);
        assertEquals(new Range(1.0, 5.0), result,
            "shift(Range(3,7), -2.0) should return Range(1,5)");
    }

    // allowZeroCrossing = true: bounds cross zero freely
    @Test
    void shift_AllowZeroCrossing_PositiveDelta_CrossesZero() {
        Range result = Range.shift(new Range(-5.0, -1.0), 3.0, true);
        assertEquals(new Range(-2.0, 2.0), result,
            "shift(Range(-5,-1), +3, true) should return Range(-2, 2) crossing zero");
    }

    @Test
    void shift_AllowZeroCrossing_NegativeDelta_CrossesZero() {
        Range result = Range.shift(new Range(1.0, 5.0), -3.0, true);
        assertEquals(new Range(-2.0, 2.0), result,
            "shift(Range(1,5), -3, true) should return Range(-2, 2) crossing zero");
    }

    // allowZeroCrossing = false, positive range shifted left past zero → clamped at 0
    @Test
    void shift_NoZeroCrossing_PositiveRangeShiftedLeft_ClampsAtZero() {
        // lower=2 > 0: Math.max(2+(-10), 0) = 0
        // upper=4 > 0: Math.max(4+(-10), 0) = 0
        Range result = Range.shift(new Range(2.0, 4.0), -10.0, false);
        assertEquals(new Range(0.0, 0.0), result,
            "shift(Range(2,4), -10, false) should clamp both bounds at 0");
    }

    // allowZeroCrossing = false, negative range shifted right past zero → clamped at 0
    @Test
    void shift_NoZeroCrossing_NegativeRangeShiftedRight_ClampsAtZero() {
        // lower=-4 < 0: Math.min(-4+10, 0) = 0
        // upper=-2 < 0: Math.min(-2+10, 0) = 0
        Range result = Range.shift(new Range(-4.0, -2.0), 10.0, false);
        assertEquals(new Range(0.0, 0.0), result,
            "shift(Range(-4,-2), +10, false) should clamp both bounds at 0");
    }

    // allowZeroCrossing = false, lower bound exactly 0 → else branch (value + delta)
    @Test
    void shift_NoZeroCrossing_LowerBoundZero_ShiftsFreely() {
        // lower=0: else branch → 0 + 3 = 3
        // upper=5 > 0: Math.max(5+3, 0) = 8
        Range result = Range.shift(new Range(0.0, 5.0), 3.0, false);
        assertEquals(new Range(3.0, 8.0), result,
            "shift(Range(0,5), +3, false): lower=0 uses else branch → Range(3,8)");
    }

    // ==================== 2.4 scale(Range, double) ====================

    @Test
    void scale_PositiveFactor_ScalesBothBounds() {
        Range result = Range.scale(new Range(2.0, 4.0), 2.0);
        assertEquals(new Range(4.0, 8.0), result,
            "scale(Range(2,4), 2.0) should return Range(4,8)");
    }

    @Test
    void scale_ZeroFactor_ReturnZeroRange() {
        Range result = Range.scale(new Range(2.0, 4.0), 0.0);
        assertEquals(new Range(0.0, 0.0), result,
            "scale(Range(2,4), 0.0) should return Range(0,0)");
    }

    @Test
    void scale_NegativeFactor_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
            () -> Range.scale(new Range(2.0, 4.0), -1.0),
            "scale with negative factor should throw IllegalArgumentException");
    }

    // ==================== 2.5 equals(Object) ====================

    @Test
    void equals_SameBounds_ReturnsTrue() {
        Range r1 = new Range(1.0, 5.0);
        Range r2 = new Range(1.0, 5.0);
        assertTrue(r1.equals(r2),
            "Two Range objects with same bounds should be equal");
    }

    @Test
    void equals_DifferentLower_ReturnsFalse() {
        assertFalse(new Range(1.0, 5.0).equals(new Range(2.0, 5.0)),
            "Ranges with different lower bounds should not be equal");
    }

    @Test
    void equals_DifferentUpper_ReturnsFalse() {
        assertFalse(new Range(1.0, 5.0).equals(new Range(1.0, 6.0)),
            "Ranges with different upper bounds should not be equal");
    }

    @Test
    void equals_NonRangeObject_ReturnsFalse() {
        assertFalse(new Range(1.0, 5.0).equals("not a range"),
            "Range.equals() with a non-Range object should return false");
    }

    @Test
    void equals_Null_ReturnsFalse() {
        assertFalse(new Range(1.0, 5.0).equals(null),
            "Range.equals(null) should return false");
    }

    // ==================== 2.5 hashCode() ====================

    @Test
    void hashCode_EqualRanges_SameHashCode() {
        Range r1 = new Range(1.0, 5.0);
        Range r2 = new Range(1.0, 5.0);
        assertEquals(r1.hashCode(), r2.hashCode(),
            "Equal ranges must have the same hashCode");
    }

    @Test
    void hashCode_DifferentRanges_DifferentHashCode() {
        Range r1 = new Range(1.0, 5.0);
        Range r2 = new Range(2.0, 6.0);
        assertNotEquals(r1.hashCode(), r2.hashCode(),
            "Different ranges should (very likely) have different hashCodes");
    }

    // ==================== 2.5 isNaNRange() ====================

    @Test
    void isNaNRange_BothBoundsNaN_ReturnsTrue() {
        Range r = new Range(Double.NaN, Double.NaN);
        assertTrue(r.isNaNRange(),
            "Range(NaN, NaN).isNaNRange() should return true");
    }

    @Test
    void isNaNRange_NoBoundsNaN_ReturnsFalse() {
        assertFalse(new Range(1.0, 5.0).isNaNRange(),
            "Range(1, 5).isNaNRange() should return false");
    }

    @Test
    void isNaNRange_OnlyUpperNaN_ReturnsFalse() {
        Range r = new Range(1.0, Double.NaN);
        assertFalse(r.isNaNRange(),
            "Range(1.0, NaN).isNaNRange() should return false (lower is not NaN)");
    }

    // ==================== 2.5 toString() ====================

    @Test
    void toString_ReturnsCorrectFormat() {
        Range r = new Range(1.5, 3.5);
        assertEquals("Range[1.5,3.5]", r.toString(),
            "toString() should return 'Range[1.5,3.5]'");
    }

    // ==================== Infeasible guard paths via reflection ====================
    // getLowerBound(), getUpperBound(), and getLength() all contain a defensive
    // "if (lower > upper) throw" guard. The constructor already enforces lower <= upper,
    // so these branches are permanently unreachable through normal construction.
    // Reflection is used here solely to cover those bytecode instructions and
    // push instruction coverage past 90%.

    private static Range makeInvalidRange() throws Exception {
        Range r = new Range(1.0, 2.0);
        Field lowerField = Range.class.getDeclaredField("lower");
        lowerField.setAccessible(true);
        lowerField.set(r, 99.0);   // force lower (99) > upper (2)
        return r;
    }

    @Test
    void getLowerBound_InternallyInvalidRange_ThrowsIllegalArgumentException() throws Exception {
        Range r = makeInvalidRange();
        assertThrows(IllegalArgumentException.class, r::getLowerBound,
            "getLowerBound() should throw when lower > upper");
    }

    @Test
    void getUpperBound_InternallyInvalidRange_ThrowsIllegalArgumentException() throws Exception {
        Range r = makeInvalidRange();
        assertThrows(IllegalArgumentException.class, r::getUpperBound,
            "getUpperBound() should throw when lower > upper");
    }

    @Test
    void getLength_InternallyInvalidRange_ThrowsIllegalArgumentException() throws Exception {
        Range r = makeInvalidRange();
        assertThrows(IllegalArgumentException.class, r::getLength,
            "getLength() should throw when lower > upper");
    }

}

