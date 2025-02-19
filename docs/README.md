# Astraea User Guide

![](https://changjy81.github.io/ip/Ui.png)

A bored deity offers to track your tasks for you. Don't question it.

Astraea is a task tracking program with command-line interface.   
Add, list or remove tasks, mark or unmark tasks as
completed, search through tasks based on name, and set your own aliases for all built-in commands.

>Command format below places user-specified input in [square brackets]. All other text should exactly match.

## Features

### Adding Tasks: `todo`, `deadline`, `event`

Creates a new task with custom task names and date parameters.

- **Todo** tasks with name only
  - Format: `todo [name]`
- **Deadline** tasks with name and deadline time
  - Format: `deadline [name] /by [time]`
  - `[time]` can be tracked as date/datetime if in format `YYYY-MM-DD`/`YYYY-MM-DD HH:MM`
- **Event** tasks with name, start time and end time
  - Format: `event [name] /from [time] /to [time]`
  - `[time]` can be tracked as date/datetime if **_both arguments_** are in format`YYYY-MM-DD`/`YYYY-MM-DD HH:MM`

Examples: 
- `deadline Project submission /by end of weekend`
- `event Project meeting /from 2025-02-20 22:00 /to 2025-02-21 00:00`

### Listing Tasks: `list`

Shows all the tasks currently tracked, their index and their completion state.

Format: `list`

### Mark/Unmark Tasks

Sets a task as done or not done. Warns user when index is out of bounds.

Format `mark [index]` or `unmark [index]`, with `[index]` as a single numeric integer

### Deleting Tasks: `delete`

Deletes a task. Warns user when index is out of bounds.

Format: `delete [index]`, with `[index]` as a single numeric integer

### Find Tasks: `find`

Searches through all currently tracked tasks and lists any that contains the search term in its name.

Format: `find [search term]`

### Add Aliases: `add_alias`

Adds a new alias for a built-in command, allowing you to use that alias instead of the full command name.  
Warns the user if the command name is invalid, or if the alias name is already in use.

Format: `add_alias [command name] [alias name]`

`[command name]` must always be the full command name, regardless of whether you have previously set an alias.

Example:
```angular2html
add_alias todo t
t Make new task
```

### Remove Aliases: `remove_alias`

Removes an existing alias for a built-in command. Warns the user if the alias name cannot be found.  

Format: `remove_alias [alias name]`

## Saving the data

Astraea automatically saves the tasks and aliases you create whenever their state is updated.  
There is no need to save manually.