# Full Calendar

## Initialization

- create an calendar instace
- dynamise the instance whith js

```html
<div id='calendar'></div>
```

``` javascript
<script>
    document.addEventListener('DOMContentLoaded', function() {
        var calendarEl = document.getElementById('calendar');   // recuperer l'instance html du calendrier

        /**
         * Créer une instance de FullCalendar
         * et configurer les options
        */ 
        var calendar = new FullCalendar.Calendar(calendarEl, {
        });
        // Afficer le calendrier
        calendar.render();
    });
</script>
```

## Options

### initialView

- Type: `String`
- Default: `dayGridMonth`
- Description: The initial view to display when the calendar is first rendered.

### headerToolbar

- Type: `Object`
- Default: `{ left: 'prev,next today', center: 'title', right: 'dayGridMonth,timeGridWeek,timeGridDay' }`
- Description: Configuration for the header toolbar
  - `left`: Buttons on the left side of the header.
  - `center`: Title of the calendar.
  - `right`: Buttons on the right side of the header.

``` javascript
headerToolbar: { center: 'dayGridMonth,timeGridWeek' }, // buttons for switching between views
```

## Handlers

### dateClick

- Type: `Function`
- Description: Callback function that is triggered when a date is clicked.

```javascript
dateClick: function() {
    alert('a day has been clicked!');
}

// OR

calendar.on('dateClick', function(info) {
  console.log('clicked on ' + info.dateStr);
});

```

## Methods

Methods provide ways to manipulate the calendar from JavaScript code. A method operates on a Calendar object that has already been initialized:

### render

Render the calendar. This is typically called after the calendar has been initialized or when options have changed.

``` javascript
calendar.render();
```

### next

Move the calendar to the next time period (e.g., next month or next week, depending on the current view).

``` javascript
calendar.next();
```
