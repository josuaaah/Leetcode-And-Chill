class Layout

    EMPTY = "L"
    FLOOR = "."
    OCCUPIED = "#"

    def initialize grid
        @cells = self.class.initialize_cells(grid)
    end

    def update_until_stable
        is_stable = false
        until is_stable
            is_changed = update_all
            is_stable = !is_changed
        end
    end

    # Updates every cell, returns true if at least one cell changed
    def update_all
        has_at_least_one_changed = false

        @cells.each() do |row|
            row.each() do |cell|
                set_next_state(cell)
            end
        end

        @cells.each() do |row|
            row.each() do |cell|
                is_changed_cell = advance_state(cell)
                if is_changed_cell
                    has_at_least_one_changed = true
                end
            end
        end

        return has_at_least_one_changed
    end

    def get_state cell
        return cell["state"]
    end

    def get_neighbours cell
        return cell["neighbours"]
    end

    def get_next_state cell
        return cell["next state"]
    end

    def get_num_occupied
        count = 0
        @cells.each() do |row|
            row.each() do |cell|
                if get_state(cell) == OCCUPIED
                    count += 1
                end
            end
        end
        return count
    end

    def display
        @cells.each do |row|
            row.each do |cell|
                print "#{get_state(cell)} "
            end
            print "\n"
        end
    end

    private

    # Assigns a value to the "next state" key for the cell
    def set_next_state cell
    	current_state = get_state(cell)

        # No change to state if cell is a floor
        if current_state == FLOOR
            cell["next state"] = current_state
        end

        num_adjacent_occupied = get_neighbours(cell)
            .map{ |pos| get_state(@cells[pos[0]][pos[1]]) }
            .count(OCCUPIED)

        if current_state == EMPTY && num_adjacent_occupied == 0
            cell["next state"] = OCCUPIED
        elsif current_state == OCCUPIED && num_adjacent_occupied >= 4
            cell["next state"] = EMPTY
        else
            cell["next state"] = current_state
        end

    end

    def advance_state cell
        is_changed = get_state(cell) != get_next_state(cell)
        cell["state"] = get_next_state(cell)
        cell["next state"] = nil
        return is_changed
    end

    def self.initialize_cells grid
        max_row = grid.length() - 1
        max_col = grid[0].length() - 1
        cells = []
        for row in 0..max_row
            cells_row = []
            for col in 0..max_col
                cell = {
                    "state" => grid[row][col],
                    "next state" => nil,
                    "neighbours" => self.initialize_neighbours(
                        row, col, 0, max_row, 0, max_col)
                }
                cells_row.push(cell)
            end
            cells.push(cells_row)
        end
        return cells
    end

    def self.initialize_neighbours row, col, min_row, max_row, min_col, max_col
        neighbours = []
        for r in (row - 1)..(row + 1) do
            for c in (col - 1)..(col + 1) do
                is_valid_row = r >= min_row && r <= max_row
                is_valid_col = c >= min_col && c <= max_col
                if (is_valid_row && is_valid_col)
                    neighbours.push([r, c])
                end
            end
        end
        neighbours.delete([row, col])
        return neighbours
    end
end


def readInput
    lines = File.read("data.in").split
    rows = lines.map{ |line| line.chars }
    return rows
end


grid = readInput()

layout = Layout.new(grid)

layout.update_until_stable

puts layout.get_num_occupied
