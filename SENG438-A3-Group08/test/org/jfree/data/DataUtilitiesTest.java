package org.jfree.data;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.jfree.data.DataUtilities;
import org.jfree.data.KeyedValues;
import org.jfree.data.Values2D;
import org.junit.jupiter.api.Test;

public class DataUtilitiesTest {

    // ============================================================
    // calculateColumnTotal(Values2D, int)
    // ============================================================

    // PASS
    @Test
    public void calculateColumnTotal_validData_validColumn() {
        Values2D mockData = mock(Values2D.class);
        when(mockData.getRowCount()).thenReturn(3);
        when(mockData.getValue(0, 0)).thenReturn(1.0);
        when(mockData.getValue(1, 0)).thenReturn(2.0);
        when(mockData.getValue(2, 0)).thenReturn(3.0);
        double result = DataUtilities.calculateColumnTotal(mockData, 0);
        assertEquals(6.0, result, 0.0000001);
    }

    // PASS
    @Test
    public void calculateColumnTotal_invalidColumn_returnsZero() {
        Values2D mockData = mock(Values2D.class);
        when(mockData.getRowCount()).thenReturn(0);
        double result = DataUtilities.calculateColumnTotal(mockData, 5);
        assertEquals(0.0, result, 0.0000001);
    }

    // PASS
    @Test
    public void calculateColumnTotal_nullData_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            DataUtilities.calculateColumnTotal(null, 0);
        });
    }

    // PASS
    @Test
    public void calculateColumnTotal_singleRow_returnsCorrectSum() {
        Values2D mockData = mock(Values2D.class);
        when(mockData.getRowCount()).thenReturn(1);
        when(mockData.getValue(0, 0)).thenReturn(42.0);
        double result = DataUtilities.calculateColumnTotal(mockData, 0);
        assertEquals(42.0, result, 0.0000001);
    }

    // PASS
    @Test
    public void calculateColumnTotal_emptyTable_returnsZero() {
        Values2D mockData = mock(Values2D.class);
        when(mockData.getRowCount()).thenReturn(0);
        double result = DataUtilities.calculateColumnTotal(mockData, 0);
        assertEquals(0.0, result, 0.0000001);
    }

    // PASS
    @Test
    public void calculateColumnTotal_negativeValues_returnsCorrectSum() {
        Values2D mockData = mock(Values2D.class);
        when(mockData.getRowCount()).thenReturn(3);
        when(mockData.getValue(0, 0)).thenReturn(-1.0);
        when(mockData.getValue(1, 0)).thenReturn(-2.0);
        when(mockData.getValue(2, 0)).thenReturn(-3.0);
        double result = DataUtilities.calculateColumnTotal(mockData, 0);
        assertEquals(-6.0, result, 0.0000001);
    }

    // PASS
    @Test
    public void calculateColumnTotal_mixedValues_returnsCorrectSum() {
        Values2D mockData = mock(Values2D.class);
        when(mockData.getRowCount()).thenReturn(4);
        when(mockData.getValue(0, 0)).thenReturn(10.0);
        when(mockData.getValue(1, 0)).thenReturn(-5.0);
        when(mockData.getValue(2, 0)).thenReturn(3.0);
        when(mockData.getValue(3, 0)).thenReturn(-2.0);
        double result = DataUtilities.calculateColumnTotal(mockData, 0);
        assertEquals(6.0, result, 0.0000001);
    }

    // PASS
    @Test
    public void calculateColumnTotal_allZeros_returnsZero() {
        Values2D mockData = mock(Values2D.class);
        when(mockData.getRowCount()).thenReturn(3);
        when(mockData.getValue(0, 0)).thenReturn(0.0);
        when(mockData.getValue(1, 0)).thenReturn(0.0);
        when(mockData.getValue(2, 0)).thenReturn(0.0);
        double result = DataUtilities.calculateColumnTotal(mockData, 0);
        assertEquals(0.0, result, 0.0000001);
    }

    // PASS
    @Test
    public void calculateColumnTotal_largeValues_returnsCorrectSum() {
        Values2D mockData = mock(Values2D.class);
        when(mockData.getRowCount()).thenReturn(2);
        when(mockData.getValue(0, 0)).thenReturn(Double.MAX_VALUE / 2);
        when(mockData.getValue(1, 0)).thenReturn(Double.MAX_VALUE / 2);
        double result = DataUtilities.calculateColumnTotal(mockData, 0);
        assertEquals(Double.MAX_VALUE, result, Double.MAX_VALUE * 0.0001);
    }

    // PASS
    @Test
    public void calculateColumnTotal_verySmallValues_returnsCorrectSum() {
        Values2D mockData = mock(Values2D.class);
        when(mockData.getRowCount()).thenReturn(3);
        when(mockData.getValue(0, 0)).thenReturn(1e-10);
        when(mockData.getValue(1, 0)).thenReturn(2e-10);
        when(mockData.getValue(2, 0)).thenReturn(3e-10);
        double result = DataUtilities.calculateColumnTotal(mockData, 0);
        assertEquals(6e-10, result, 1e-20);
    }

    // PASS
    @Test
    public void calculateColumnTotal_nullValuesInColumn_treatsAsZero() {
        Values2D mockData = mock(Values2D.class);
        when(mockData.getRowCount()).thenReturn(3);
        when(mockData.getValue(0, 0)).thenReturn(5.0);
        when(mockData.getValue(1, 0)).thenReturn(null);
        when(mockData.getValue(2, 0)).thenReturn(3.0);
        double result = DataUtilities.calculateColumnTotal(mockData, 0);
        assertEquals(8.0, result, 0.0000001);
    }

    // PASS
    @Test
    public void calculateColumnTotal_negativeColumnIndex_returnsZero() {
        Values2D mockData = mock(Values2D.class);
        when(mockData.getRowCount()).thenReturn(3);
        double result = DataUtilities.calculateColumnTotal(mockData, -1);
        assertEquals(0.0, result, 0.0000001);
    }

    // PASS
    @Test
    public void calculateColumnTotal_manyRows_returnsCorrectSum() {
        Values2D mockData = mock(Values2D.class);
        int rowCount = 1000;
        when(mockData.getRowCount()).thenReturn(rowCount);
        for (int i = 0; i < rowCount; i++) {
            when(mockData.getValue(i, 0)).thenReturn(1.0);
        }
        double result = DataUtilities.calculateColumnTotal(mockData, 0);
        assertEquals(1000.0, result, 0.0000001);
    }

    // PASS
    @Test
    public void calculateColumnTotal_decimalValues_returnsCorrectSum() {
        Values2D mockData = mock(Values2D.class);
        when(mockData.getRowCount()).thenReturn(3);
        when(mockData.getValue(0, 0)).thenReturn(1.5);
        when(mockData.getValue(1, 0)).thenReturn(2.7);
        when(mockData.getValue(2, 0)).thenReturn(3.3);
        double result = DataUtilities.calculateColumnTotal(mockData, 0);
        assertEquals(7.5, result, 0.0000001);
    }

    // ============================================================
    // calculateColumnTotal(Values2D, int, int[])
    // ============================================================

    @Test
    public void calculateColumnTotal_withValidRows_sumsOnlyValidRows() {
        Values2D mockData = mock(Values2D.class);
        when(mockData.getRowCount()).thenReturn(3);
        when(mockData.getValue(0, 0)).thenReturn(1.0);
        when(mockData.getValue(1, 0)).thenReturn(2.0);
        when(mockData.getValue(2, 0)).thenReturn(3.0);
        int[] validRows = {0, 2};
        double result = DataUtilities.calculateColumnTotal(mockData, 0, validRows);
        assertEquals(4.0, result, 0.0000001);
    }

    // Yazin
    @Test
    public void calculateColumnTotal_withValidRows_ignoresOutOfBoundsRows() {
        Values2D mockData = mock(Values2D.class);
        when(mockData.getRowCount()).thenReturn(2);
        when(mockData.getValue(0, 0)).thenReturn(1.0);
        when(mockData.getValue(1, 0)).thenReturn(2.0);
        int[] validRows = {0, 5};
        double result = DataUtilities.calculateColumnTotal(mockData, 0, validRows);
        assertEquals(1.0, result, 0.0000001);
    }

    // ============================================================
    // calculateRowTotal(Values2D, int)
    // ============================================================

    // PASS
    @Test
    public void calculateRowTotal_validData_validRow() {
        Values2D mockData = mock(Values2D.class);
        when(mockData.getRowCount()).thenReturn(1);
        when(mockData.getColumnCount()).thenReturn(3);
        when(mockData.getValue(0, 0)).thenReturn(1.0);
        when(mockData.getValue(0, 1)).thenReturn(2.0);
        when(mockData.getValue(0, 2)).thenReturn(3.0);
        double result = DataUtilities.calculateRowTotal(mockData, 0);
        assertEquals(6.0, result, 0.0000001);
    }

    // PASS
    @Test
    public void calculateRowTotal_invalidRow_returnsZero() {
        Values2D mockData = mock(Values2D.class);
        when(mockData.getColumnCount()).thenReturn(0);
        double result = DataUtilities.calculateRowTotal(mockData, 5);
        assertEquals(0.0, result, 0.0000001);
    }

    // PASS
    @Test
    public void calculateRowTotal_nullData_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            DataUtilities.calculateRowTotal(null, 0);
        });
    }

    // PASS
    @Test
    public void calculateRowTotal_singleColumn_returnsCorrectSum() {
        Values2D mockData = mock(Values2D.class);
        when(mockData.getColumnCount()).thenReturn(1);
        when(mockData.getValue(0, 0)).thenReturn(42.0);
        double result = DataUtilities.calculateRowTotal(mockData, 0);
        assertEquals(42.0, result, 0.0000001);
    }

    // PASS
    @Test
    public void calculateRowTotal_emptyTable_returnsZero() {
        Values2D mockData = mock(Values2D.class);
        when(mockData.getColumnCount()).thenReturn(0);
        double result = DataUtilities.calculateRowTotal(mockData, 0);
        assertEquals(0.0, result, 0.0000001);
    }

    // PASS
    @Test
    public void calculateRowTotal_negativeValues_returnsCorrectSum() {
        Values2D mockData = mock(Values2D.class);
        when(mockData.getColumnCount()).thenReturn(3);
        when(mockData.getValue(0, 0)).thenReturn(-1.0);
        when(mockData.getValue(0, 1)).thenReturn(-2.0);
        when(mockData.getValue(0, 2)).thenReturn(-3.0);
        double result = DataUtilities.calculateRowTotal(mockData, 0);
        assertEquals(-6.0, result, 0.0000001);
    }

    // FAIL - Implementation bug
    @Test
    public void calculateRowTotal_mixedValues_returnsCorrectSum() {
        Values2D mockData = mock(Values2D.class);
        when(mockData.getColumnCount()).thenReturn(4);
        when(mockData.getValue(0, 0)).thenReturn(10.0);
        when(mockData.getValue(0, 1)).thenReturn(-5.0);
        when(mockData.getValue(0, 2)).thenReturn(3.0);
        when(mockData.getValue(0, 3)).thenReturn(-2.0);
        double result = DataUtilities.calculateRowTotal(mockData, 0);
        assertEquals(6.0, result, 0.0000001);
    }

    // PASS
    @Test
    public void calculateRowTotal_allZeros_returnsZero() {
        Values2D mockData = mock(Values2D.class);
        when(mockData.getColumnCount()).thenReturn(3);
        when(mockData.getValue(0, 0)).thenReturn(0.0);
        when(mockData.getValue(0, 1)).thenReturn(0.0);
        when(mockData.getValue(0, 2)).thenReturn(0.0);
        double result = DataUtilities.calculateRowTotal(mockData, 0);
        assertEquals(0.0, result, 0.0000001);
    }

    // PASS
    @Test
    public void calculateRowTotal_largeValues_returnsCorrectSum() {
        Values2D mockData = mock(Values2D.class);
        when(mockData.getColumnCount()).thenReturn(2);
        when(mockData.getValue(0, 0)).thenReturn(Double.MAX_VALUE / 2);
        when(mockData.getValue(0, 1)).thenReturn(Double.MAX_VALUE / 2);
        double result = DataUtilities.calculateRowTotal(mockData, 0);
        assertEquals(Double.MAX_VALUE, result, Double.MAX_VALUE * 0.0001);
    }

    // PASS
    @Test
    public void calculateRowTotal_verySmallValues_returnsCorrectSum() {
        Values2D mockData = mock(Values2D.class);
        when(mockData.getColumnCount()).thenReturn(3);
        when(mockData.getValue(0, 0)).thenReturn(1e-10);
        when(mockData.getValue(0, 1)).thenReturn(2e-10);
        when(mockData.getValue(0, 2)).thenReturn(3e-10);
        double result = DataUtilities.calculateRowTotal(mockData, 0);
        assertEquals(6e-10, result, 1e-20);
    }

    // PASS
    @Test
    public void calculateRowTotal_nullValuesInRow_treatsAsZero() {
        Values2D mockData = mock(Values2D.class);
        when(mockData.getColumnCount()).thenReturn(3);
        when(mockData.getValue(0, 0)).thenReturn(5.0);
        when(mockData.getValue(0, 1)).thenReturn(null);
        when(mockData.getValue(0, 2)).thenReturn(3.0);
        double result = DataUtilities.calculateRowTotal(mockData, 0);
        assertEquals(8.0, result, 0.0000001);
    }

    // PASS
    @Test
    public void calculateRowTotal_negativeRowIndex_returnsZero() {
        Values2D mockData = mock(Values2D.class);
        when(mockData.getColumnCount()).thenReturn(3);
        double result = DataUtilities.calculateRowTotal(mockData, -1);
        assertEquals(0.0, result, 0.0000001);
    }

    // PASS
    @Test
    public void calculateRowTotal_manyColumns_returnsCorrectSum() {
        Values2D mockData = mock(Values2D.class);
        int columnCount = 1000;
        when(mockData.getColumnCount()).thenReturn(columnCount);
        for (int i = 0; i < columnCount; i++) {
            when(mockData.getValue(0, i)).thenReturn(1.0);
        }
        double result = DataUtilities.calculateRowTotal(mockData, 0);
        assertEquals(1000.0, result, 0.0000001);
    }

    // PASS
    @Test
    public void calculateRowTotal_decimalValues_returnsCorrectSum() {
        Values2D mockData = mock(Values2D.class);
        when(mockData.getColumnCount()).thenReturn(3);
        when(mockData.getValue(0, 0)).thenReturn(1.5);
        when(mockData.getValue(0, 1)).thenReturn(2.7);
        when(mockData.getValue(0, 2)).thenReturn(3.3);
        double result = DataUtilities.calculateRowTotal(mockData, 0);
        assertEquals(7.5, result, 0.0000001);
    }

    // PASS
    @Test
    public void calculateRowTotal_secondRow_returnsCorrectSum() {
        Values2D mockData = mock(Values2D.class);
        when(mockData.getColumnCount()).thenReturn(3);
        when(mockData.getValue(1, 0)).thenReturn(4.0);
        when(mockData.getValue(1, 1)).thenReturn(5.0);
        when(mockData.getValue(1, 2)).thenReturn(6.0);
        double result = DataUtilities.calculateRowTotal(mockData, 1);
        assertEquals(15.0, result, 0.0000001);
    }

    // ============================================================
    // calculateRowTotal(Values2D, int, int[])
    // ============================================================

    @Test
    public void calculateRowTotal_withValidColumns_sumsOnlyValidColumns() {
        Values2D mockData = mock(Values2D.class);
        when(mockData.getColumnCount()).thenReturn(3);
        when(mockData.getValue(0, 0)).thenReturn(1.0);
        when(mockData.getValue(0, 1)).thenReturn(2.0);
        when(mockData.getValue(0, 2)).thenReturn(3.0);
        int[] validCols = {0, 2};
        double result = DataUtilities.calculateRowTotal(mockData, 0, validCols);
        assertEquals(4.0, result, 0.0000001);
    }

    @Test
    public void calculateRowTotal_withValidColumns_ignoresOutOfBoundsColumns() {
        Values2D mockData = mock(Values2D.class);
        when(mockData.getColumnCount()).thenReturn(2);
        when(mockData.getValue(0, 0)).thenReturn(1.0);
        when(mockData.getValue(0, 1)).thenReturn(2.0);
        int[] validCols = {0, 3};
        double result = DataUtilities.calculateRowTotal(mockData, 0, validCols);
        assertEquals(1.0, result, 0.0000001);
    }

    // ============================================================
    // equal(double[][], double[][])
    // ============================================================

    // yazin
    @Test
    public void equal_bothNull_returnsTrue() {
        assertTrue(DataUtilities.equal(null, null),
            "equal(null, null) should return true");
    }

    //yazin
    @Test
    public void equal_oneNull_returnsFalse() {
        assertFalse(DataUtilities.equal(new double[][]{{1.0, 2.0}}, null),
            "equal(nonNull, null) should return false");
    }

    @Test
    public void equal_differentDimensions_returnsFalse() {
        double[][] a = {{1.0}, {2.0}};
        double[][] b = {{1.0}};
        assertFalse(DataUtilities.equal(a, b),
            "Arrays with different row counts should not be equal");
    }

    @Test
    public void equal_sameDimensionsDifferentValues_returnsFalse() {
        assertFalse(DataUtilities.equal(new double[][]{{1.0, 2.0}}, new double[][]{{1.0, 3.0}}),
            "Arrays with different contents should not be equal");
    }

    @Test
    public void equal_identicalArrays_returnsTrue() {
        double[][] a = {{1.0, 2.0}, {3.0, 4.0}};
        double[][] b = {{1.0, 2.0}, {3.0, 4.0}};
        assertTrue(DataUtilities.equal(a, b),
            "Identical arrays should be equal");
    }

    // ============================================================
    // clone(double[][])
    // ============================================================

    @Test
    public void clone_nullSource_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> DataUtilities.clone(null));
    }

    @Test
    public void clone_copiesValuesAndPreservesNullRows() {
        double[][] source = {{1.0, 2.0}, null, {3.0}};
        double[][] copy = DataUtilities.clone(source);
        assertNotSame(source, copy);
        assertArrayEquals(source[0], copy[0], 0.0000001);
        assertNull(copy[1]);
        assertArrayEquals(source[2], copy[2], 0.0000001);
    }

    // ============================================================
    // getCumulativePercentages(KeyedValues)
    // ============================================================

    // FAIL - Implementation behaves differently
    @Test
    public void getCumulativePercentages_validData_returnsCorrectValues() {
        KeyedValues mockData = mock(KeyedValues.class);
        when(mockData.getItemCount()).thenReturn(3);
        when(mockData.getKey(0)).thenReturn("A");
        when(mockData.getKey(1)).thenReturn("B");
        when(mockData.getKey(2)).thenReturn("C");
        when(mockData.getValue(0)).thenReturn(Double.valueOf(5.0));
        when(mockData.getValue(1)).thenReturn(Double.valueOf(9.0));
        when(mockData.getValue(2)).thenReturn(Double.valueOf(2.0));
        KeyedValues result = DataUtilities.getCumulativePercentages(mockData);
        assertEquals(0.3125, result.getValue(0).doubleValue(), 0.0000001);
        assertEquals(0.875,  result.getValue(1).doubleValue(), 0.0000001);
        assertEquals(1.0,    result.getValue(2).doubleValue(), 0.0000001);
    }

    // PASS
    @Test
    public void getCumulativePercentages_nullData_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> DataUtilities.getCumulativePercentages(null));
    }

    // PASS
    @Test
    public void getCumulativePercentages_singleItem_returnsOne() {
        KeyedValues mockData = mock(KeyedValues.class);
        when(mockData.getItemCount()).thenReturn(1);
        when(mockData.getKey(0)).thenReturn("A");
        when(mockData.getValue(0)).thenReturn(Double.valueOf(10.0));
        KeyedValues result = DataUtilities.getCumulativePercentages(mockData);
        assertEquals(1.0, result.getValue(0).doubleValue(), 0.0000001);
    }

    // PASS
    @Test
    public void getCumulativePercentages_twoEqualItems_returnsCorrectPercentages() {
        KeyedValues mockData = mock(KeyedValues.class);
        when(mockData.getItemCount()).thenReturn(2);
        when(mockData.getKey(0)).thenReturn("A");
        when(mockData.getKey(1)).thenReturn("B");
        when(mockData.getValue(0)).thenReturn(Double.valueOf(5.0));
        when(mockData.getValue(1)).thenReturn(Double.valueOf(5.0));
        KeyedValues result = DataUtilities.getCumulativePercentages(mockData);
        assertEquals(0.5, result.getValue(0).doubleValue(), 0.0000001);
        assertEquals(1.0, result.getValue(1).doubleValue(), 0.0000001);
    }

    // PASS
    @Test
    public void getCumulativePercentages_negativeValues_returnsCorrectPercentages() {
        KeyedValues mockData = mock(KeyedValues.class);
        when(mockData.getItemCount()).thenReturn(3);
        when(mockData.getKey(0)).thenReturn("A");
        when(mockData.getKey(1)).thenReturn("B");
        when(mockData.getKey(2)).thenReturn("C");
        when(mockData.getValue(0)).thenReturn(Double.valueOf(-5.0));
        when(mockData.getValue(1)).thenReturn(Double.valueOf(-3.0));
        when(mockData.getValue(2)).thenReturn(Double.valueOf(-2.0));
        KeyedValues result = DataUtilities.getCumulativePercentages(mockData);
        assertEquals(-5.0 / -10.0, result.getValue(0).doubleValue(), 0.0000001);
        assertEquals(-8.0 / -10.0, result.getValue(1).doubleValue(), 0.0000001);
        assertEquals(-10.0 / -10.0, result.getValue(2).doubleValue(), 0.0000001);
    }

    // FAIL - Implementation behaves differently
    @Test
    public void getCumulativePercentages_mixedValues_returnsCorrectPercentages() {
        KeyedValues mockData = mock(KeyedValues.class);
        when(mockData.getItemCount()).thenReturn(3);
        when(mockData.getKey(0)).thenReturn("A");
        when(mockData.getKey(1)).thenReturn("B");
        when(mockData.getKey(2)).thenReturn("C");
        when(mockData.getValue(0)).thenReturn(Double.valueOf(10.0));
        when(mockData.getValue(1)).thenReturn(Double.valueOf(-5.0));
        when(mockData.getValue(2)).thenReturn(Double.valueOf(5.0));
        KeyedValues result = DataUtilities.getCumulativePercentages(mockData);
        assertEquals(10.0 / 10.0, result.getValue(0).doubleValue(), 0.0000001);
        assertEquals(5.0  / 10.0, result.getValue(1).doubleValue(), 0.0000001);
        assertEquals(10.0 / 10.0, result.getValue(2).doubleValue(), 0.0000001);
    }

    // PASS
    @Test
    public void getCumulativePercentages_largeValues_returnsCorrectPercentages() {
        KeyedValues mockData = mock(KeyedValues.class);
        when(mockData.getItemCount()).thenReturn(2);
        when(mockData.getKey(0)).thenReturn("A");
        when(mockData.getKey(1)).thenReturn("B");
        when(mockData.getValue(0)).thenReturn(Double.valueOf(1e15));
        when(mockData.getValue(1)).thenReturn(Double.valueOf(1e15));
        KeyedValues result = DataUtilities.getCumulativePercentages(mockData);
        assertEquals(0.5, result.getValue(0).doubleValue(), 0.0000001);
        assertEquals(1.0, result.getValue(1).doubleValue(), 0.0000001);
    }

    // PASS
    @Test
    public void getCumulativePercentages_verySmallValues_returnsCorrectPercentages() {
        KeyedValues mockData = mock(KeyedValues.class);
        when(mockData.getItemCount()).thenReturn(2);
        when(mockData.getKey(0)).thenReturn("A");
        when(mockData.getKey(1)).thenReturn("B");
        when(mockData.getValue(0)).thenReturn(Double.valueOf(1e-10));
        when(mockData.getValue(1)).thenReturn(Double.valueOf(1e-10));
        KeyedValues result = DataUtilities.getCumulativePercentages(mockData);
        assertEquals(0.5, result.getValue(0).doubleValue(), 0.0000001);
        assertEquals(1.0, result.getValue(1).doubleValue(), 0.0000001);
    }

    // PASS
    @Test
    public void getCumulativePercentages_nullValues_treatsAsZero() {
        KeyedValues mockData = mock(KeyedValues.class);
        when(mockData.getItemCount()).thenReturn(3);
        when(mockData.getKey(0)).thenReturn("A");
        when(mockData.getKey(1)).thenReturn("B");
        when(mockData.getKey(2)).thenReturn("C");
        when(mockData.getValue(0)).thenReturn(Double.valueOf(5.0));
        when(mockData.getValue(1)).thenReturn(null);
        when(mockData.getValue(2)).thenReturn(Double.valueOf(5.0));
        KeyedValues result = DataUtilities.getCumulativePercentages(mockData);
        assertEquals(0.5, result.getValue(0).doubleValue(), 0.0000001);
        assertEquals(0.5, result.getValue(1).doubleValue(), 0.0000001);
        assertEquals(1.0, result.getValue(2).doubleValue(), 0.0000001);
    }

    // PASS
    @Test
    public void getCumulativePercentages_manyItems_returnsCorrectPercentages() {
        KeyedValues mockData = mock(KeyedValues.class);
        int itemCount = 10;
        when(mockData.getItemCount()).thenReturn(itemCount);
        for (int i = 0; i < itemCount; i++) {
            when(mockData.getKey(i)).thenReturn("Item" + i);
            when(mockData.getValue(i)).thenReturn(Double.valueOf(10.0));
        }
        KeyedValues result = DataUtilities.getCumulativePercentages(mockData);
        assertEquals(0.1, result.getValue(0).doubleValue(), 0.0000001);
        assertEquals(0.2, result.getValue(1).doubleValue(), 0.0000001);
        assertEquals(0.5, result.getValue(4).doubleValue(), 0.0000001);
        assertEquals(1.0, result.getValue(9).doubleValue(), 0.0000001);
    }

    // PASS
    @Test
    public void getCumulativePercentages_decimalValues_returnsCorrectPercentages() {
        KeyedValues mockData = mock(KeyedValues.class);
        when(mockData.getItemCount()).thenReturn(3);
        when(mockData.getKey(0)).thenReturn("A");
        when(mockData.getKey(1)).thenReturn("B");
        when(mockData.getKey(2)).thenReturn("C");
        when(mockData.getValue(0)).thenReturn(Double.valueOf(1.5));
        when(mockData.getValue(1)).thenReturn(Double.valueOf(2.5));
        when(mockData.getValue(2)).thenReturn(Double.valueOf(1.0));
        KeyedValues result = DataUtilities.getCumulativePercentages(mockData);
        assertEquals(0.3, result.getValue(0).doubleValue(), 0.0000001);
        assertEquals(0.8, result.getValue(1).doubleValue(), 0.0000001);
        assertEquals(1.0, result.getValue(2).doubleValue(), 0.0000001);
    }

    // PASS
    @Test
    public void getCumulativePercentages_firstValueZero_returnsCorrectPercentages() {
        KeyedValues mockData = mock(KeyedValues.class);
        when(mockData.getItemCount()).thenReturn(3);
        when(mockData.getKey(0)).thenReturn("A");
        when(mockData.getKey(1)).thenReturn("B");
        when(mockData.getKey(2)).thenReturn("C");
        when(mockData.getValue(0)).thenReturn(Double.valueOf(0.0));
        when(mockData.getValue(1)).thenReturn(Double.valueOf(5.0));
        when(mockData.getValue(2)).thenReturn(Double.valueOf(5.0));
        KeyedValues result = DataUtilities.getCumulativePercentages(mockData);
        assertEquals(0.0, result.getValue(0).doubleValue(), 0.0000001);
        assertEquals(0.5, result.getValue(1).doubleValue(), 0.0000001);
        assertEquals(1.0, result.getValue(2).doubleValue(), 0.0000001);
    }

    // ============================================================
    // createNumberArray(double[])
    // ============================================================

    // FAIL - Implementation returns null Number objects
    @Test
    public void createNumberArray_multipleValues_returnsCorrectArray() {
        double[] input = {1.0, 2.5, 3.7};
        Number[] result = DataUtilities.createNumberArray(input);
        assertNotNull(result);
        assertEquals(3, result.length);
        assertNotNull(result[0]);
        assertNotNull(result[1]);
        assertNotNull(result[2]);
        assertEquals(1.0, result[0].doubleValue(), 0.0000001);
        assertEquals(2.5, result[1].doubleValue(), 0.0000001);
        assertEquals(3.7, result[2].doubleValue(), 0.0000001);
    }

    // FAIL - Implementation returns null Number objects
    @Test
    public void createNumberArray_singleValue_returnsCorrectArray() {
        double[] input = {42.0};
        Number[] result = DataUtilities.createNumberArray(input);
        assertNotNull(result);
        assertEquals(1, result.length);
        assertNotNull(result[0]);
        assertEquals(42.0, result[0].doubleValue(), 0.0000001);
    }

    // PASS
    @Test
    public void createNumberArray_emptyArray_returnsEmptyArray() {
        Number[] result = DataUtilities.createNumberArray(new double[]{});
        assertNotNull(result);
        assertEquals(0, result.length);
    }

    // FAIL - Implementation returns null Number objects
    @Test
    public void createNumberArray_negativeValues_returnsCorrectArray() {
        double[] input = {-1.0, -5.5};
        Number[] result = DataUtilities.createNumberArray(input);
        assertNotNull(result);
        assertEquals(2, result.length);
        assertNotNull(result[0]);
        assertNotNull(result[1]);
        assertEquals(-1.0, result[0].doubleValue(), 0.0000001);
        assertEquals(-5.5, result[1].doubleValue(), 0.0000001);
    }

    // FAIL - Implementation returns null Number objects
    @Test
    public void createNumberArray_zeroValues_returnsCorrectArray() {
        double[] input = {0.0, 0.0};
        Number[] result = DataUtilities.createNumberArray(input);
        assertNotNull(result);
        assertEquals(2, result.length);
        assertNotNull(result[0]);
        assertNotNull(result[1]);
        assertEquals(0.0, result[0].doubleValue(), 0.0000001);
        assertEquals(0.0, result[1].doubleValue(), 0.0000001);
    }

    // FAIL - Implementation returns null Number objects
    @Test
    public void createNumberArray_largeValues_returnsCorrectArray() {
        double[] input = {Double.MAX_VALUE};
        Number[] result = DataUtilities.createNumberArray(input);
        assertNotNull(result);
        assertEquals(1, result.length);
        assertNotNull(result[0]);
        assertEquals(Double.MAX_VALUE, result[0].doubleValue(), 0.0000001);
    }

    // PASS
    @Test
    public void createNumberArray_nullInput_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> DataUtilities.createNumberArray(null));
    }

    // FAIL - Implementation returns null Number objects
    @Test
    public void createNumberArray_mixedValues_returnsCorrectArray() {
        double[] input = {-10.5, 0.0, 10.5, -3.2, 7.8};
        Number[] result = DataUtilities.createNumberArray(input);
        assertNotNull(result);
        assertEquals(5, result.length);
        for (int i = 0; i < result.length; i++) assertNotNull(result[i]);
        assertEquals(-10.5, result[0].doubleValue(), 0.0000001);
        assertEquals(0.0,   result[1].doubleValue(), 0.0000001);
        assertEquals(10.5,  result[2].doubleValue(), 0.0000001);
        assertEquals(-3.2,  result[3].doubleValue(), 0.0000001);
        assertEquals(7.8,   result[4].doubleValue(), 0.0000001);
    }

    // FAIL - Implementation returns null Number objects
    @Test
    public void createNumberArray_minPositiveValue_returnsCorrectArray() {
        double[] input = {Double.MIN_VALUE};
        Number[] result = DataUtilities.createNumberArray(input);
        assertNotNull(result);
        assertEquals(1, result.length);
        assertNotNull(result[0]);
        assertEquals(Double.MIN_VALUE, result[0].doubleValue(), 0.0);
    }

    // FAIL - Implementation returns null Number objects
    @Test
    public void createNumberArray_maxNegativeValue_returnsCorrectArray() {
        double[] input = {-Double.MAX_VALUE};
        Number[] result = DataUtilities.createNumberArray(input);
        assertNotNull(result);
        assertEquals(1, result.length);
        assertNotNull(result[0]);
        assertEquals(-Double.MAX_VALUE, result[0].doubleValue(), 0.0000001);
    }

    // FAIL - Implementation returns null Number objects
    @Test
    public void createNumberArray_positiveInfinity_returnsCorrectArray() {
        double[] input = {Double.POSITIVE_INFINITY};
        Number[] result = DataUtilities.createNumberArray(input);
        assertNotNull(result);
        assertEquals(1, result.length);
        assertNotNull(result[0]);
        assertEquals(Double.POSITIVE_INFINITY, result[0].doubleValue(), 0.0);
    }

    // FAIL - Implementation returns null Number objects
    @Test
    public void createNumberArray_negativeInfinity_returnsCorrectArray() {
        double[] input = {Double.NEGATIVE_INFINITY};
        Number[] result = DataUtilities.createNumberArray(input);
        assertNotNull(result);
        assertEquals(1, result.length);
        assertNotNull(result[0]);
        assertEquals(Double.NEGATIVE_INFINITY, result[0].doubleValue(), 0.0);
    }

    // FAIL - Implementation returns null Number objects
    @Test
    public void createNumberArray_nanValue_returnsCorrectArray() {
        double[] input = {Double.NaN};
        Number[] result = DataUtilities.createNumberArray(input);
        assertNotNull(result);
        assertEquals(1, result.length);
        assertNotNull(result[0]);
        assertTrue(Double.isNaN(result[0].doubleValue()));
    }

    // FAIL - Implementation returns null Number objects
    @Test
    public void createNumberArray_specialValues_returnsCorrectArray() {
        double[] input = {Double.MAX_VALUE, Double.MIN_VALUE, Double.POSITIVE_INFINITY,
                          Double.NEGATIVE_INFINITY, Double.NaN};
        Number[] result = DataUtilities.createNumberArray(input);
        assertNotNull(result);
        assertEquals(5, result.length);
        for (int i = 0; i < result.length; i++) assertNotNull(result[i]);
        assertEquals(Double.MAX_VALUE,        result[0].doubleValue(), 0.0000001);
        assertEquals(Double.MIN_VALUE,        result[1].doubleValue(), 0.0);
        assertEquals(Double.POSITIVE_INFINITY, result[2].doubleValue(), 0.0);
        assertEquals(Double.NEGATIVE_INFINITY, result[3].doubleValue(), 0.0);
        assertTrue(Double.isNaN(result[4].doubleValue()));
    }

    // FAIL - Implementation returns null Number objects
    @Test
    public void createNumberArray_twoElements_returnsCorrectArray() {
        double[] input = {1.5, 2.5};
        Number[] result = DataUtilities.createNumberArray(input);
        assertNotNull(result);
        assertEquals(2, result.length);
        assertNotNull(result[0]);
        assertNotNull(result[1]);
        assertEquals(1.5, result[0].doubleValue(), 0.0000001);
        assertEquals(2.5, result[1].doubleValue(), 0.0000001);
    }

    // FAIL - Implementation returns null Number objects
    @Test
    public void createNumberArray_largeArray_returnsCorrectArray() {
        int size = 1000;
        double[] input = new double[size];
        for (int i = 0; i < size; i++) input[i] = i * 1.5;
        Number[] result = DataUtilities.createNumberArray(input);
        assertNotNull(result);
        assertEquals(size, result.length);
        assertNotNull(result[0]);
        assertNotNull(result[size / 2]);
        assertNotNull(result[size - 1]);
        assertEquals(0.0,              result[0].doubleValue(),        0.0000001);
        assertEquals((size / 2) * 1.5, result[size / 2].doubleValue(), 0.0000001);
        assertEquals((size - 1) * 1.5, result[size - 1].doubleValue(), 0.0000001);
    }

    // FAIL - Implementation returns null Number objects
    @Test
    public void createNumberArray_verySmallValues_returnsCorrectArray() {
        double[] input = {0.0000001, -0.0000001, 1e-10, -1e-10};
        Number[] result = DataUtilities.createNumberArray(input);
        assertNotNull(result);
        assertEquals(4, result.length);
        for (int i = 0; i < result.length; i++) assertNotNull(result[i]);
        assertEquals(0.0000001,  result[0].doubleValue(), 1e-15);
        assertEquals(-0.0000001, result[1].doubleValue(), 1e-15);
        assertEquals(1e-10,      result[2].doubleValue(), 1e-20);
        assertEquals(-1e-10,     result[3].doubleValue(), 1e-20);
    }

    // ============================================================
    // createNumberArray2D(double[][])
    // ============================================================

    // FAIL - NullPointerException: implementation returns null Number objects in 2D arrays
    @Test
    public void createNumberArray2D_2x2Matrix_returnsCorrectArray() {
        double[][] input = {{1.0, 2.0}, {3.0, 4.0}};
        Number[][] result = DataUtilities.createNumberArray2D(input);
        assertNotNull(result);
        assertEquals(2, result.length);
        assertEquals(2, result[0].length);
        assertEquals(2, result[1].length);
        assertEquals(1.0, result[0][0].doubleValue(), 0.0000001);
        assertEquals(2.0, result[0][1].doubleValue(), 0.0000001);
        assertEquals(3.0, result[1][0].doubleValue(), 0.0000001);
        assertEquals(4.0, result[1][1].doubleValue(), 0.0000001);
    }

    // FAIL
    @Test
    public void createNumberArray2D_singleRow_returnsCorrectArray() {
        double[][] input = {{1.5, 2.5, 3.5, 4.5}};
        Number[][] result = DataUtilities.createNumberArray2D(input);
        assertNotNull(result);
        assertEquals(1, result.length);
        assertEquals(4, result[0].length);
        assertEquals(1.5, result[0][0].doubleValue(), 0.0000001);
        assertEquals(4.5, result[0][3].doubleValue(), 0.0000001);
    }

    // FAIL
    @Test
    public void createNumberArray2D_singleColumn_returnsCorrectArray() {
        double[][] input = {{1.0}, {2.0}, {3.0}};
        Number[][] result = DataUtilities.createNumberArray2D(input);
        assertNotNull(result);
        assertEquals(3, result.length);
        assertEquals(1.0, result[0][0].doubleValue(), 0.0000001);
        assertEquals(2.0, result[1][0].doubleValue(), 0.0000001);
        assertEquals(3.0, result[2][0].doubleValue(), 0.0000001);
    }

    // FAIL
    @Test
    public void createNumberArray2D_singleElement_returnsCorrectArray() {
        Number[][] result = DataUtilities.createNumberArray2D(new double[][]{{42.0}});
        assertNotNull(result);
        assertEquals(1, result.length);
        assertEquals(1, result[0].length);
        assertEquals(42.0, result[0][0].doubleValue(), 0.0000001);
    }

    // PASS
    @Test
    public void createNumberArray2D_emptyOuterArray_returnsEmptyArray() {
        Number[][] result = DataUtilities.createNumberArray2D(new double[][]{});
        assertNotNull(result);
        assertEquals(0, result.length);
    }

    // PASS
    @Test
    public void createNumberArray2D_emptyInnerArrays_returnsCorrectArray() {
        Number[][] result = DataUtilities.createNumberArray2D(new double[][]{{}, {}, {}});
        assertNotNull(result);
        assertEquals(3, result.length);
        assertEquals(0, result[0].length);
        assertEquals(0, result[1].length);
        assertEquals(0, result[2].length);
    }

    // FAIL
    @Test
    public void createNumberArray2D_jaggedArray_returnsCorrectArray() {
        double[][] input = {{1.0, 2.0, 3.0}, {4.0, 5.0}, {6.0, 7.0, 8.0, 9.0}};
        Number[][] result = DataUtilities.createNumberArray2D(input);
        assertNotNull(result);
        assertEquals(3, result.length);
        assertEquals(3, result[0].length);
        assertEquals(2, result[1].length);
        assertEquals(4, result[2].length);
        assertEquals(1.0, result[0][0].doubleValue(), 0.0000001);
        assertEquals(9.0, result[2][3].doubleValue(), 0.0000001);
    }

    // FAIL
    @Test
    public void createNumberArray2D_mixedValues_returnsCorrectArray() {
        double[][] input = {{-5.5, 0.0, 5.5}, {-10.0, 10.0, -1.0}, {0.0, 0.0, 0.0}};
        Number[][] result = DataUtilities.createNumberArray2D(input);
        assertNotNull(result);
        assertEquals(3, result.length);
        assertEquals(-5.5,  result[0][0].doubleValue(), 0.0000001);
        assertEquals(0.0,   result[0][1].doubleValue(), 0.0000001);
        assertEquals(10.0,  result[1][1].doubleValue(), 0.0000001);
    }

    // FAIL
    @Test
    public void createNumberArray2D_specialValues_returnsCorrectArray() {
        double[][] input = {
            {Double.MAX_VALUE, Double.MIN_VALUE},
            {Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY},
            {Double.NaN, 0.0}
        };
        Number[][] result = DataUtilities.createNumberArray2D(input);
        assertNotNull(result);
        assertEquals(3, result.length);
        assertEquals(Double.MAX_VALUE,         result[0][0].doubleValue(), 0.0000001);
        assertEquals(Double.POSITIVE_INFINITY, result[1][0].doubleValue(), 0.0);
        assertEquals(Double.NEGATIVE_INFINITY, result[1][1].doubleValue(), 0.0);
        assertTrue(Double.isNaN(result[2][0].doubleValue()));
    }

    // FAIL
    @Test
    public void createNumberArray2D_largeArray_returnsCorrectArray() {
        int rows = 100, cols = 100;
        double[][] input = new double[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                input[i][j] = i + j * 0.1;
        Number[][] result = DataUtilities.createNumberArray2D(input);
        assertNotNull(result);
        assertEquals(rows, result.length);
        assertEquals(0.0,           result[0][0].doubleValue(),   0.0000001);
        assertEquals(99.0 + 99*0.1, result[99][99].doubleValue(), 0.0000001);
    }

    // FAIL
    @Test
    public void createNumberArray2D_verySmallValues_returnsCorrectArray() {
        double[][] input = {{1e-10, -1e-10}, {0.0000001, -0.0000001}};
        Number[][] result = DataUtilities.createNumberArray2D(input);
        assertNotNull(result);
        assertEquals(2, result.length);
        assertEquals(1e-10,     result[0][0].doubleValue(), 1e-20);
        assertEquals(-1e-10,    result[0][1].doubleValue(), 1e-20);
        assertEquals(0.0000001, result[1][0].doubleValue(), 1e-15);
    }

    // FAIL
    @Test
    public void createNumberArray2D_rectangularMatrix_returnsCorrectArray() {
        double[][] input = {
            {1.0, 2.0, 3.0, 4.0, 5.0},
            {6.0, 7.0, 8.0, 9.0, 10.0},
            {11.0, 12.0, 13.0, 14.0, 15.0}
        };
        Number[][] result = DataUtilities.createNumberArray2D(input);
        assertNotNull(result);
        assertEquals(3,  result.length);
        assertEquals(5,  result[0].length);
        assertEquals(1.0,  result[0][0].doubleValue(), 0.0000001);
        assertEquals(5.0,  result[0][4].doubleValue(), 0.0000001);
        assertEquals(15.0, result[2][4].doubleValue(), 0.0000001);
    }

    // FAIL
    @Test
    public void createNumberArray2D_allZeros_returnsCorrectArray() {
        double[][] input = {{0.0, 0.0, 0.0}, {0.0, 0.0, 0.0}};
        Number[][] result = DataUtilities.createNumberArray2D(input);
        assertNotNull(result);
        assertEquals(2, result.length);
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 3; j++)
                assertEquals(0.0, result[i][j].doubleValue(), 0.0000001);
    }

    // FAIL
    @Test
    public void createNumberArray2D_allNegative_returnsCorrectArray() {
        double[][] input = {{-1.0, -2.0}, {-3.0, -4.0}, {-5.0, -6.0}};
        Number[][] result = DataUtilities.createNumberArray2D(input);
        assertNotNull(result);
        assertEquals(3, result.length);
        assertEquals(-1.0, result[0][0].doubleValue(), 0.0000001);
        assertEquals(-6.0, result[2][1].doubleValue(), 0.0000001);
    }

    // PASS
    @Test
    public void createNumberArray2D_nullInput_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> DataUtilities.createNumberArray2D(null));
    }
}
