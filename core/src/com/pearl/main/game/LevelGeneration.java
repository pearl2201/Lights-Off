package com.pearl.main.game;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;

public class LevelGeneration {
	private int[] region_size;
	private int[] region_of;
	private int max_solution_length;
	private int size;
	private boolean[][] sol;

	public LevelGeneration() {

	}

	public void init(int size) {
		this.size = size;
		int[][] adj_matrix = new int[size * size][size * size];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				for (int m = 0; m < size; m++)
					for (int n = 0; n < size; n++)
						adj_matrix[i * size + j][m * size + n] = (m - i) * (m - i) + (n - j) * (n - j) <= 1 ? 1 : 0;

		ArrayList<Integer> non_pivot_cols = new ArrayList<Integer>();
		int ipiv = 0;
		for (int jpiv = 0; jpiv < size * size; jpiv++) {

			boolean is_pivot_col = false;
			for (int i = ipiv; i < size * size; i++) {
				if (adj_matrix[i][jpiv] != 0) {

					/* Swap rows */
					if (i != ipiv) {
						for (int z = 0; z < size * size; z++) {
							int t = adj_matrix[i][z];
							adj_matrix[i][z] = adj_matrix[ipiv][z];
							adj_matrix[ipiv][z] = t;
						}
					}

					for (int j = ipiv + 1; j < size * size; j++) {
						if (adj_matrix[j][jpiv] != 0) {
							for (int k = 0; k < size * size; k++)
								adj_matrix[j][k] ^= adj_matrix[ipiv][k];
						}
					}
					is_pivot_col = true;
					ipiv++;
					break;
				}
			}

			if (!is_pivot_col)
				non_pivot_cols.add(jpiv);
		}

		int[][] basis_for_ns = new int[non_pivot_cols.size()][size * size];
		int n = 0;
		for (int col : non_pivot_cols) {
			for (int j = 0; j < size * size; j++)
				basis_for_ns[n][j] = 0;
			basis_for_ns[n][col] = 1;

			for (int i = size * size - 1; i >= 0; i--) {
				int jpiv = 0;
				for (; jpiv < size * size; jpiv++)
					if (adj_matrix[i][jpiv] != 0)
						break;
				if (jpiv == size * size)
					continue;
				for (int j = jpiv + 1; j < size * size; j++)
					basis_for_ns[n][jpiv] ^= adj_matrix[i][j] * basis_for_ns[n][j];
			}

			n++;
		}

		// A button's region # is a binary # with 1's in a place corresponding
		// to any null-vector which contains it.
		region_size = new int[1 << non_pivot_cols.size()];
		for (int j = 0; j < region_size.length; j++)
			region_size[j] = 0;
		region_of = new int[size * size];
		for (int i = 0; i < size * size; i++) {
			region_of[i] = 0;
			for (int j = 0; j < non_pivot_cols.size(); j++) {

				if (basis_for_ns[j][i] != 0) {
					region_of[i] += 1 << j;
				}
			}
			region_size[region_of[i]]++;

		}

		max_solution_length = region_size[0];
		for (int j = 1; j < region_size.length; j++)
			max_solution_length += (int) Math.floor(region_size[j] / 2);
		System.out.println("max Solution length " + max_solution_length);
	}

	public boolean[][] createSolution(int solution_length) {
		sol = new boolean[size][size];
		// khoi tao board tra ve
		for (int x = 0; x < size; x++)
			for (int y = 0; y < size; y++)
				sol[x][y] = false;

		int[] presses_in_region = new int[region_size.length];
		for (int i = 0; i < region_size.length; i++)
			presses_in_region[i] = 0;

		/*
		 * Note this should be Random.int_range (0, 3) but it is like this to
		 * match the old behaviour
		 */
		int sym = MathUtils.random(0, 3);

		int presses_left = solution_length < max_solution_length ? solution_length : max_solution_length;
		while (presses_left > 0) {
			int[] x = new int[2];
			int[] y = new int[2];
			;

			// Pick a spot (x[0], y[0]), a corner if one is needed
			/*
			 * Note this should be Random.int_range (0, size) but it is like
			 * this to match the old behaviour
			 */
			x[0] = (int) MathUtils.round((size - 1) * MathUtils.random());
			y[0] = (int) MathUtils.round((size - 1) * MathUtils.random());

			// Also pick a symmetric spot, to take if possible
			if (sym == 0) {
				x[1] = size - 1 - x[0];
				y[1] = y[0];
			} else if (sym == 1) {
				x[1] = size - 1 - x[0];
				y[1] = size - 1 - y[0];
			} else {
				x[1] = x[0];
				y[1] = size - 1 - y[0];
			}

			// Make each move if it doesn't fill a region more than halfway.
			for (int k = 0; k < 2; k++) {
				int r = region_of[x[k] * size + y[k]];
				if (r == 0 || 2 * (presses_in_region[r] + 1) <= region_size[r]) {
					if (sol[x[k]][y[k]])
						continue;
					sol[x[k]][y[k]] = true;
					presses_in_region[r]++;
					presses_left--;
				}
				if (presses_left == 0)
					break;
			}
		}
		return sol;

	}

	
}
