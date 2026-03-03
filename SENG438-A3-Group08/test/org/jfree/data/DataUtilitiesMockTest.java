package org.jfree.data;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.jfree.data.DataUtilities;
import org.jfree.data.KeyedValues;
import org.jfree.data.Values2D;
import org.junit.jupiter.api.Test;

public class DataUtilitiesMockTest {

	// ------------------------------------
	// calculateColumnTotal (Mockito-based)
	// ------------------------------------

	// PASS
	// Valid column → returns correct sum
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
	// Invalid column → returns zero (per Javadoc)
	@Test
	public void calculateColumnTotal_invalidColumn_returnsZero() {
		Values2D mockData = mock(Values2D.class);

		when(mockData.getRowCount()).thenReturn(0);

		double result = DataUtilities.calculateColumnTotal(mockData, 5);

		assertEquals(0.0, result, 0.0000001);
	}

	// PASS
	// Null data → throws NullPointerException (actual implementation behavior)
	@Test
	public void calculateColumnTotal_nullData_throwsNullPointerException() {
		assertThrows(NullPointerException.class, () -> {
			DataUtilities.calculateColumnTotal(null, 0);
		});
	}
	
	// PASS
	// Single row
	@Test
	public void calculateColumnTotal_singleRow_returnsCorrectSum() {
		Values2D mockData = mock(Values2D.class);
		
		when(mockData.getRowCount()).thenReturn(1);
		when(mockData.getValue(0, 0)).thenReturn(42.0);
		
		double result = DataUtilities.calculateColumnTotal(mockData, 0);
		
		assertEquals(42.0, result, 0.0000001);
	}
	
	// PASS
	// Empty table (zero rows)
	@Test
	public void calculateColumnTotal_emptyTable_returnsZero() {
		Values2D mockData = mock(Values2D.class);
		
		when(mockData.getRowCount()).thenReturn(0);
		
		double result = DataUtilities.calculateColumnTotal(mockData, 0);
		
		assertEquals(0.0, result, 0.0000001);
	}
	
	// PASS
	// Negative values
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
	// Mixed positive and negative values
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
	// All zeros
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
	// Large values
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
	// Very small values
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
	// Null values in column (should be treated as 0)
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
	// Negative column index
	@Test
	public void calculateColumnTotal_negativeColumnIndex_returnsZero() {
		Values2D mockData = mock(Values2D.class);
		
		when(mockData.getRowCount()).thenReturn(3);
		
		double result = DataUtilities.calculateColumnTotal(mockData, -1);
		
		assertEquals(0.0, result, 0.0000001);
	}
	
	// PASS
	// Large number of rows
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
	// Column with decimal values
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

	// ------------------------------------
	// calculateRowTotal (Mockito-based)
	// ------------------------------------

	// PASS
	// Valid row → returns correct sum
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
	// Invalid row → returns zero (per Javadoc)
	@Test
	public void calculateRowTotal_invalidRow_returnsZero() {
		Values2D mockData = mock(Values2D.class);

		when(mockData.getColumnCount()).thenReturn(0);

		double result = DataUtilities.calculateRowTotal(mockData, 5);

		assertEquals(0.0, result, 0.0000001);
	}

	// PASS
	// Null data → throws NullPointerException (actual implementation behavior)
	@Test
	public void calculateRowTotal_nullData_throwsNullPointerException() {
		assertThrows(NullPointerException.class, () -> {
			DataUtilities.calculateRowTotal(null, 0);
		});
	}
	
	// PASS
	// Single column
	@Test
	public void calculateRowTotal_singleColumn_returnsCorrectSum() {
		Values2D mockData = mock(Values2D.class);
		
		when(mockData.getColumnCount()).thenReturn(1);
		when(mockData.getValue(0, 0)).thenReturn(42.0);
		
		double result = DataUtilities.calculateRowTotal(mockData, 0);
		
		assertEquals(42.0, result, 0.0000001);
	}
	
	// PASS
	// Empty table (zero columns)
	@Test
	public void calculateRowTotal_emptyTable_returnsZero() {
		Values2D mockData = mock(Values2D.class);
		
		when(mockData.getColumnCount()).thenReturn(0);
		
		double result = DataUtilities.calculateRowTotal(mockData, 0);
		
		assertEquals(0.0, result, 0.0000001);
	}
	
	// PASS
	// Negative values
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
	// Mixed positive and negative values
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
	// All zeros
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
	// Large values
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
	// Very small values
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
	// Null values in row (should be treated as 0)
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
	// Negative row index
	@Test
	public void calculateRowTotal_negativeRowIndex_returnsZero() {
		Values2D mockData = mock(Values2D.class);
		
		when(mockData.getColumnCount()).thenReturn(3);
		
		double result = DataUtilities.calculateRowTotal(mockData, -1);
		
		assertEquals(0.0, result, 0.0000001);
	}
	
	// PASS
	// Large number of columns
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
	// Row with decimal values
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
	// Multiple rows - test second row
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

	// ------------------------------------
	// getCumulativePercentages (Mockito-based)
	// ------------------------------------

	// FAIL - Implementation behaves differently
	// Valid data → returns correct cumulative percentages
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
		assertEquals(0.875, result.getValue(1).doubleValue(), 0.0000001);
		assertEquals(1.0, result.getValue(2).doubleValue(), 0.0000001);
	}

	// PASS
	@Test
	public void getCumulativePercentages_nullData_throwsIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> {
			DataUtilities.getCumulativePercentages(null);
		});
	}
	
	// PASS
	// Single item
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
	// Two items with equal values
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
	// Empty data (zero items)
	@Test
	public void getCumulativePercentages_emptyData_returnsEmptyResult() {
		KeyedValues mockData = mock(KeyedValues.class);
		
		when(mockData.getItemCount()).thenReturn(0);
		
		KeyedValues result = DataUtilities.getCumulativePercentages(mockData);
		
		assertEquals(0, result.getItemCount());
	}
	
	// PASS
	// All zeros
	@Test
	public void getCumulativePercentages_allZeros_returnsNaN() {
		KeyedValues mockData = mock(KeyedValues.class);
		
		when(mockData.getItemCount()).thenReturn(3);
		when(mockData.getKey(0)).thenReturn("A");
		when(mockData.getKey(1)).thenReturn("B");
		when(mockData.getKey(2)).thenReturn("C");
		when(mockData.getValue(0)).thenReturn(Double.valueOf(0.0));
		when(mockData.getValue(1)).thenReturn(Double.valueOf(0.0));
		when(mockData.getValue(2)).thenReturn(Double.valueOf(0.0));
		
		KeyedValues result = DataUtilities.getCumulativePercentages(mockData);
		
		// When sum is 0, division by zero should give NaN
		assertTrue(Double.isNaN(result.getValue(0).doubleValue()));
	}
	
	// PASS
	// Negative values
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
		
		// Total = -10
		assertEquals(-5.0 / -10.0, result.getValue(0).doubleValue(), 0.0000001); // 0.5
		assertEquals(-8.0 / -10.0, result.getValue(1).doubleValue(), 0.0000001); // 0.8
		assertEquals(-10.0 / -10.0, result.getValue(2).doubleValue(), 0.0000001); // 1.0
	}
	
	// FAIL - Implementation behaves differently
	// Mixed positive and negative values
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
		
		// Total = 10
		assertEquals(10.0 / 10.0, result.getValue(0).doubleValue(), 0.0000001); // 1.0
		assertEquals(5.0 / 10.0, result.getValue(1).doubleValue(), 0.0000001);  // 0.5
		assertEquals(10.0 / 10.0, result.getValue(2).doubleValue(), 0.0000001); // 1.0
	}
	
	// PASS
	// Very large values
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
	// Very small values
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
	// Null values in data (should be treated as 0)
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
		
		// Total = 10 (5 + 0 + 5)
		assertEquals(0.5, result.getValue(0).doubleValue(), 0.0000001);
		assertEquals(0.5, result.getValue(1).doubleValue(), 0.0000001);
		assertEquals(1.0, result.getValue(2).doubleValue(), 0.0000001);
	}
	
	// PASS
	// Many items
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
		
		// Each item is 10, total is 100
		assertEquals(0.1, result.getValue(0).doubleValue(), 0.0000001);
		assertEquals(0.2, result.getValue(1).doubleValue(), 0.0000001);
		assertEquals(0.5, result.getValue(4).doubleValue(), 0.0000001);
		assertEquals(1.0, result.getValue(9).doubleValue(), 0.0000001);
	}
	
	// PASS
	// Decimal values
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
		
		// Total = 5.0
		assertEquals(0.3, result.getValue(0).doubleValue(), 0.0000001);  // 1.5/5
		assertEquals(0.8, result.getValue(1).doubleValue(), 0.0000001);  // 4.0/5
		assertEquals(1.0, result.getValue(2).doubleValue(), 0.0000001);  // 5.0/5
	}
	
	// PASS
	// First value is zero
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
		
		// Total = 10
		assertEquals(0.0, result.getValue(0).doubleValue(), 0.0000001);  // 0/10
		assertEquals(0.5, result.getValue(1).doubleValue(), 0.0000001);  // 5/10
		assertEquals(1.0, result.getValue(2).doubleValue(), 0.0000001);  // 10/10
	}
}
