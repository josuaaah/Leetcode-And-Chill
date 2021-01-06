class Layout
    EMPTY = "L"
    FLOOR = "."
    OCCUPIED = "#"

    def initialize grid
        @min_row = 0
        @min_col = 0
        @max_row = grid.length() - 1
        @max_col = grid[0].length() - 1
        @cells = initialize_cells(grid)
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

    def get_cell(row, col)
        return @cells[row][col]
    end

    def is_seat_at(row, col)
        return get_state(get_cell(row, col)) != FLOOR
    end

    def is_occupied_at(row, col)
        return get_state(get_cell(row, col)) == OCCUPIED
    end

    def get_state cell
        return cell["state"]
    end

    def get_position cell
        return cell["position"]
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
                print "#{get_num_neighbours_occupied(cell)} "
            end
            print "\n"
        end
    end

    def get_num_neighbours_occupied cell
        row = get_position(cell)[0]
        col = get_position(cell)[1]
        
        num_occupied = 0

        # Down neighbour
        for r in (row + 1)..@max_row do
            if is_seat_at(r, col)
                if is_occupied_at(r, col)
                    num_occupied += 1
                end
                break
            end
        end

        # Up neighbour
        for r in (row - 1).downto(@min_row) do
            if is_seat_at(r, col)
                if is_occupied_at(r, col)
                    num_occupied += 1
                end
                break
            end
        end

        # Right neighbour
        for c in (col + 1)..@max_col do
            if is_seat_at(row, c)
                if is_occupied_at(row, c)
                    num_occupied += 1
                end
                break
            end
        end

        # Left neighbour
        for c in (col - 1).downto(@min_col) do
            if is_seat_at(row, c)
                if is_occupied_at(row, c)
                    num_occupied += 1
                end
                break
            end
        end

        # Left upper diagonal neighbour
        r = row - 1
        c = col - 1
        until r < @min_row || c < @min_col
            if is_seat_at(r, c)
                if is_occupied_at(r, c)
                    num_occupied += 1
                end
                break
            end
            r -= 1
            c -= 1
        end

        # Right upper diagonal neighbour
        r = row - 1
        c = col + 1
        until r < @min_row || c > @max_col
            if is_seat_at(r, c)
                if is_occupied_at(r, c)
                    num_occupied += 1
                end
                break
            end
            r -= 1
            c += 1
        end

        # Left lower diagonal neighbour
        r = row + 1
        c = col - 1
        until r > @max_row || c < @min_col
            if is_seat_at(r, c)
                if is_occupied_at(r, c)
                    num_occupied += 1
                end
                break
            end
            r += 1
            c -= 1
        end

        # Right lower diagonal neighbour
        r = row + 1
        c = col + 1
        until r > @max_row || c > @max_col
            if is_seat_at(r, c)
                if is_occupied_at(r, c)
                    num_occupied += 1
                end
                break
            end
            r += 1
            c += 1
        end

        return num_occupied
    end

    private

    # Assigns a value to the "next state" key for the cell
    def set_next_state cell
        current_state = get_state(cell)

        # No change to state if cell is a floor
        if current_state == FLOOR
            cell["next state"] = current_state
        end

        num_adjacent_occupied = get_num_neighbours_occupied(cell)

        if current_state == EMPTY && num_adjacent_occupied == 0
            cell["next state"] = OCCUPIED
        elsif current_state == OCCUPIED && num_adjacent_occupied >= 5
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

    def initialize_cells grid
        cells = []
        for row in 0..@max_row
            cells_row = []
            for col in 0..@max_col
                cell = {
                    "position" => [row, col],
                    "state" => grid[row][col],
                    "next state" => nil
                }
                cells_row.push(cell)
            end
            cells.push(cells_row)
        end
        return cells
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
