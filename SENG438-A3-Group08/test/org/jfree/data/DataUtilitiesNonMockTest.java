package org.jfree.data;

import static org.junit.jupiter.api.Assertions.*;

import org.jfree.data.DataUtilities;
import org.junit.jupiter.api.Test;

public class DataUtilitiesNonMockTest {

    // ------------------------------------
    // createNumberArray (Non-mocking)
    // ------------------------------------
	
    // FAIL - Implementation returns null Number objects
    // Valid multiple values → returns correct Number[]
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
    // Single value
    @Test
    public void createNumberArray_singleValue_returnsCorrectArray() {
        double[] input = {42.0};

        Number[] result = DataUtilities.createNumberArray(input);

        assertNotNull(result);
        assertEquals(1, result.length);
        assertNotNull(result[0]);
        assertEquals(42.0, result[0].doubleValue(), 0.0000001);
    }

    // PASS - Empty array works correctly
    // Empty array
    @Test
    public void createNumberArray_emptyArray_returnsEmptyArray() {
        double[] input = {};

        Number[] result = DataUtilities.createNumberArray(input);

        assertNotNull(result);
        assertEquals(0, result.length);
    }
    
    // FAIL - Implementation returns null Number objects
    // Negative values
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
    // Zero values
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
    // Large value
    @Test
    public void createNumberArray_largeValues_returnsCorrectArray() {
        double[] input = {Double.MAX_VALUE};

        Number[] result = DataUtilities.createNumberArray(input);

        assertNotNull(result);
        assertEquals(1, result.length);
        assertNotNull(result[0]);
        assertEquals(Double.MAX_VALUE, result[0].doubleValue(), 0.0000001);
    }

    // PASS - Null input correctly throws IllegalArgumentException
    // Null input → actual implementation throws IllegalArgumentException
    @Test
    public void createNumberArray_nullInput_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            DataUtilities.createNumberArray(null);
        });
    }
    
    // FAIL - Implementation returns null Number objects
    // Mixed positive and negative values
    @Test
    public void createNumberArray_mixedValues_returnsCorrectArray() {
        double[] input = {-10.5, 0.0, 10.5, -3.2, 7.8};

        Number[] result = DataUtilities.createNumberArray(input);

        assertNotNull(result);
        assertEquals(5, result.length);
        
        assertNotNull(result[0]);
        assertNotNull(result[1]);
        assertNotNull(result[2]);
        assertNotNull(result[3]);
        assertNotNull(result[4]);
        
        assertEquals(-10.5, result[0].doubleValue(), 0.0000001);
        assertEquals(0.0, result[1].doubleValue(), 0.0000001);
        assertEquals(10.5, result[2].doubleValue(), 0.0000001);
        assertEquals(-3.2, result[3].doubleValue(), 0.0000001);
        assertEquals(7.8, result[4].doubleValue(), 0.0000001);
    }
    
    // FAIL - Implementation returns null Number objects
    // Minimum positive value (Double.MIN_VALUE)
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
    // Maximum negative value (-Double.MAX_VALUE)
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
    // Positive infinity
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
    // Negative infinity
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
    // NaN (Not a Number)
    @Test
    public void createNumberArray_nanValue_returnsCorrectArray() {
        double[] input = {Double.NaN};

        Number[] result = DataUtilities.createNumberArray(input);

        assertNotNull(result);
        assertEquals(1, result.length);
        assertNotNull(result[0]);
        assertTrue(Double.isNaN(result[0].doubleValue()), 
                   "Result should contain NaN");
    }
    
    // FAIL - Implementation returns null Number objects
    // Multiple special values combined
    @Test
    public void createNumberArray_specialValues_returnsCorrectArray() {
        double[] input = {
            Double.MAX_VALUE, 
            Double.MIN_VALUE, 
            Double.POSITIVE_INFINITY, 
            Double.NEGATIVE_INFINITY, 
            Double.NaN
        };

        Number[] result = DataUtilities.createNumberArray(input);

        assertNotNull(result);
        assertEquals(5, result.length);
        
        for (int i = 0; i < result.length; i++) {
            assertNotNull(result[i]);
        }
        
        assertEquals(Double.MAX_VALUE, result[0].doubleValue(), 0.0000001);
        assertEquals(Double.MIN_VALUE, result[1].doubleValue(), 0.0);
        assertEquals(Double.POSITIVE_INFINITY, result[2].doubleValue(), 0.0);
        assertEquals(Double.NEGATIVE_INFINITY, result[3].doubleValue(), 0.0);
        assertTrue(Double.isNaN(result[4].doubleValue()));
    }
    
    // FAIL - Implementation returns null Number objects
    // Two element array (boundary case)
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
    // Large array (stress test)
    @Test
    public void createNumberArray_largeArray_returnsCorrectArray() {
        int size = 1000;
        double[] input = new double[size];
        for (int i = 0; i < size; i++) {
            input[i] = i * 1.5;
        }

        Number[] result = DataUtilities.createNumberArray(input);

        assertNotNull(result);
        assertEquals(size, result.length);
        
        // Check first, middle, and last elements
        assertNotNull(result[0]);
        assertNotNull(result[size / 2]);
        assertNotNull(result[size - 1]);
        
        assertEquals(0.0, result[0].doubleValue(), 0.0000001);
        assertEquals((size / 2) * 1.5, result[size / 2].doubleValue(), 0.0000001);
        assertEquals((size - 1) * 1.5, result[size - 1].doubleValue(), 0.0000001);
    }
    
    // FAIL - Implementation returns null Number objects
    // Very small positive and negative values
    @Test
    public void createNumberArray_verySmallValues_returnsCorrectArray() {
        double[] input = {0.0000001, -0.0000001, 1e-10, -1e-10};

        Number[] result = DataUtilities.createNumberArray(input);

        assertNotNull(result);
        assertEquals(4, result.length);
        
        for (int i = 0; i < result.length; i++) {
            assertNotNull(result[i]);
        }
        
        assertEquals(0.0000001, result[0].doubleValue(), 1e-15);
        assertEquals(-0.0000001, result[1].doubleValue(), 1e-15);
        assertEquals(1e-10, result[2].doubleValue(), 1e-20);
        assertEquals(-1e-10, result[3].doubleValue(), 1e-20);
    }
    
    // ------------------------------------
    // createNumberArray2D (Non-mocking)
    // ------------------------------------
	
    // ERROR - NullPointerException: implementation returns null Number objects in 2D arrays
    // Valid 2x2 matrix → returns correct Number[][]
    @Test
    public void createNumberArray2D_2x2Matrix_returnsCorrectArray() {
        double[][] input = {
            {1.0, 2.0},
            {3.0, 4.0}
        };

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

    // ERROR - NullPointerException: implementation returns null Number objects in 2D arrays
    // Single row, multiple columns
    @Test
    public void createNumberArray2D_singleRow_returnsCorrectArray() {
        double[][] input = {{1.5, 2.5, 3.5, 4.5}};

        Number[][] result = DataUtilities.createNumberArray2D(input);

        assertNotNull(result);
        assertEquals(1, result.length);
        assertEquals(4, result[0].length);
        
        assertEquals(1.5, result[0][0].doubleValue(), 0.0000001);
        assertEquals(2.5, result[0][1].doubleValue(), 0.0000001);
        assertEquals(3.5, result[0][2].doubleValue(), 0.0000001);
        assertEquals(4.5, result[0][3].doubleValue(), 0.0000001);
    }

    // ERROR - NullPointerException: implementation returns null Number objects in 2D arrays
    // Multiple rows, single column
    @Test
    public void createNumberArray2D_singleColumn_returnsCorrectArray() {
        double[][] input = {
            {1.0},
            {2.0},
            {3.0}
        };

        Number[][] result = DataUtilities.createNumberArray2D(input);

        assertNotNull(result);
        assertEquals(3, result.length);
        assertEquals(1, result[0].length);
        assertEquals(1, result[1].length);
        assertEquals(1, result[2].length);
        
        assertEquals(1.0, result[0][0].doubleValue(), 0.0000001);
        assertEquals(2.0, result[1][0].doubleValue(), 0.0000001);
        assertEquals(3.0, result[2][0].doubleValue(), 0.0000001);
    }

    // ERROR - NullPointerException: implementation returns null Number objects in 2D arrays
    // Single element (1x1)
    @Test
    public void createNumberArray2D_singleElement_returnsCorrectArray() {
        double[][] input = {{42.0}};

        Number[][] result = DataUtilities.createNumberArray2D(input);

        assertNotNull(result);
        assertEquals(1, result.length);
        assertEquals(1, result[0].length);
        assertEquals(42.0, result[0][0].doubleValue(), 0.0000001);
    }

    // PASS - Empty outer array works correctly
    // Empty outer array
    @Test
    public void createNumberArray2D_emptyOuterArray_returnsEmptyArray() {
        double[][] input = {};

        Number[][] result = DataUtilities.createNumberArray2D(input);

        assertNotNull(result);
        assertEquals(0, result.length);
    }

    // PASS - Empty inner arrays work correctly  
    // Empty inner arrays (jagged with empty rows)
    @Test
    public void createNumberArray2D_emptyInnerArrays_returnsCorrectArray() {
        double[][] input = {{}, {}, {}};

        Number[][] result = DataUtilities.createNumberArray2D(input);

        assertNotNull(result);
        assertEquals(3, result.length);
        assertEquals(0, result[0].length);
        assertEquals(0, result[1].length);
        assertEquals(0, result[2].length);
    }

    // ERROR - NullPointerException: implementation returns null Number objects in 2D arrays
    // Jagged array (different row lengths)
    @Test
    public void createNumberArray2D_jaggedArray_returnsCorrectArray() {
        double[][] input = {
            {1.0, 2.0, 3.0},
            {4.0, 5.0},
            {6.0, 7.0, 8.0, 9.0}
        };

        Number[][] result = DataUtilities.createNumberArray2D(input);

        assertNotNull(result);
        assertEquals(3, result.length);
        assertEquals(3, result[0].length);
        assertEquals(2, result[1].length);
        assertEquals(4, result[2].length);
        
        assertEquals(1.0, result[0][0].doubleValue(), 0.0000001);
        assertEquals(3.0, result[0][2].doubleValue(), 0.0000001);
        assertEquals(4.0, result[1][0].doubleValue(), 0.0000001);
        assertEquals(5.0, result[1][1].doubleValue(), 0.0000001);
        assertEquals(9.0, result[2][3].doubleValue(), 0.0000001);
    }

    // ERROR - NullPointerException: implementation returns null Number objects in 2D arrays
    // Mixed positive, negative, and zero values
    @Test
    public void createNumberArray2D_mixedValues_returnsCorrectArray() {
        double[][] input = {
            {-5.5, 0.0, 5.5},
            {-10.0, 10.0, -1.0},
            {0.0, 0.0, 0.0}
        };

        Number[][] result = DataUtilities.createNumberArray2D(input);

        assertNotNull(result);
        assertEquals(3, result.length);
        
        assertEquals(-5.5, result[0][0].doubleValue(), 0.0000001);
        assertEquals(0.0, result[0][1].doubleValue(), 0.0000001);
        assertEquals(5.5, result[0][2].doubleValue(), 0.0000001);
        assertEquals(-10.0, result[1][0].doubleValue(), 0.0000001);
        assertEquals(10.0, result[1][1].doubleValue(), 0.0000001);
    }

    // ERROR - NullPointerException: implementation returns null Number objects in 2D arrays
    // Special values (MAX_VALUE, MIN_VALUE, Infinity, NaN)
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
        
        assertEquals(Double.MAX_VALUE, result[0][0].doubleValue(), 0.0000001);
        assertEquals(Double.MIN_VALUE, result[0][1].doubleValue(), 0.0);
        assertEquals(Double.POSITIVE_INFINITY, result[1][0].doubleValue(), 0.0);
        assertEquals(Double.NEGATIVE_INFINITY, result[1][1].doubleValue(), 0.0);
        assertTrue(Double.isNaN(result[2][0].doubleValue()));
        assertEquals(0.0, result[2][1].doubleValue(), 0.0000001);
    }

    // ERROR - NullPointerException: implementation returns null Number objects in 2D arrays
    // Large 2D array (stress test)
    @Test
    public void createNumberArray2D_largeArray_returnsCorrectArray() {
        int rows = 100;
        int cols = 100;
        double[][] input = new double[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                input[i][j] = i + j * 0.1;
            }
        }

        Number[][] result = DataUtilities.createNumberArray2D(input);

        assertNotNull(result);
        assertEquals(rows, result.length);
        
        // Check corners and center
        assertEquals(0.0, result[0][0].doubleValue(), 0.0000001);
        assertEquals(0.0 + 99 * 0.1, result[0][99].doubleValue(), 0.0000001);
        assertEquals(99.0, result[99][0].doubleValue(), 0.0000001);
        assertEquals(99.0 + 99 * 0.1, result[99][99].doubleValue(), 0.0000001);
        assertEquals(50.0 + 50 * 0.1, result[50][50].doubleValue(), 0.0000001);
    }

    // ERROR - NullPointerException: implementation returns null Number objects in 2D arrays
    // Very small values
    @Test
    public void createNumberArray2D_verySmallValues_returnsCorrectArray() {
        double[][] input = {
            {1e-10, -1e-10},
            {0.0000001, -0.0000001}
        };

        Number[][] result = DataUtilities.createNumberArray2D(input);

        assertNotNull(result);
        assertEquals(2, result.length);
        
        assertEquals(1e-10, result[0][0].doubleValue(), 1e-20);
        assertEquals(-1e-10, result[0][1].doubleValue(), 1e-20);
        assertEquals(0.0000001, result[1][0].doubleValue(), 1e-15);
        assertEquals(-0.0000001, result[1][1].doubleValue(), 1e-15);
    }

    // ERROR - NullPointerException: implementation returns null Number objects in 2D arrays
    // Rectangular matrix (3x5)
    @Test
    public void createNumberArray2D_rectangularMatrix_returnsCorrectArray() {
        double[][] input = {
            {1.0, 2.0, 3.0, 4.0, 5.0},
            {6.0, 7.0, 8.0, 9.0, 10.0},
            {11.0, 12.0, 13.0, 14.0, 15.0}
        };

        Number[][] result = DataUtilities.createNumberArray2D(input);

        assertNotNull(result);
        assertEquals(3, result.length);
        assertEquals(5, result[0].length);
        assertEquals(5, result[1].length);
        assertEquals(5, result[2].length);
        
        assertEquals(1.0, result[0][0].doubleValue(), 0.0000001);
        assertEquals(5.0, result[0][4].doubleValue(), 0.0000001);
        assertEquals(11.0, result[2][0].doubleValue(), 0.0000001);
        assertEquals(15.0, result[2][4].doubleValue(), 0.0000001);
    }

    // ERROR - NullPointerException: implementation returns null Number objects in 2D arrays
    // All zeros
    @Test
    public void createNumberArray2D_allZeros_returnsCorrectArray() {
        double[][] input = {
            {0.0, 0.0, 0.0},
            {0.0, 0.0, 0.0}
        };

        Number[][] result = DataUtilities.createNumberArray2D(input);

        assertNotNull(result);
        assertEquals(2, result.length);
        
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(0.0, result[i][j].doubleValue(), 0.0000001);
            }
        }
    }

    // ERROR - NullPointerException: implementation returns null Number objects in 2D arrays
    // All negative values
    @Test
    public void createNumberArray2D_allNegative_returnsCorrectArray() {
        double[][] input = {
            {-1.0, -2.0},
            {-3.0, -4.0},
            {-5.0, -6.0}
        };

        Number[][] result = DataUtilities.createNumberArray2D(input);

        assertNotNull(result);
        assertEquals(3, result.length);
        
        assertEquals(-1.0, result[0][0].doubleValue(), 0.0000001);
        assertEquals(-2.0, result[0][1].doubleValue(), 0.0000001);
        assertEquals(-6.0, result[2][1].doubleValue(), 0.0000001);
    }

    // PASS - Null input correctly throws IllegalArgumentException
    // Null input → throws IllegalArgumentException
    @Test
    public void createNumberArray2D_nullInput_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            DataUtilities.createNumberArray2D(null);
        });
    }
}
