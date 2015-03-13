

# Introduction #

YADFC(tm) has a few different options available to adjust/modify/configure.  These options are only available for editing in the GUI version of the application; the CLI version will use the last saved settings from the GUI version, or the system defaults if those are not available.

For the most part you will not need to adjust the default settings, but for those of you who are curious how to get

# Default Options #

If for whatever reason you changed your settings and you're not satisfied, there are two ways to restore your default settings:

  1. Delete the file `calc.dat` found in the directory where you ran the calculator from
  1. Manually adjust the settings back to their defaults:
    * Population Size = **256**
    * Number of Iterations = **128**
    * Number of Sets = **4**
    * Local Maxima Count = **32**
    * Elitism Rate = **10%**
    * Mutation Rate = **15%**

# Details #


---

**Note:** These settings refer to version 2.2 of Yet Another Double Fanucci Calculator(tm).  Earlier versions may not have all these options available to them.

---


There are seven main configuration options:
  * Population Size
  * Number of Iterations
  * Elitism rate
  * Mutation rate
  * Number of Sets
  * Local maxima count
  * Slot Solution Order

We will cover each of these in turn below.  Please note that all settings except for the number of sets and the slot solution order will be saved to the _calc.dat_ file (which will be saved automatically when you exit the application), whereas these other two settings will be saved as part of the Fanucci deck file, so if you wish to keep these settings, please save the deck as well.

## Population Size ##
For a genetic algorithm to function, it needs to be able to "grow/evolve", "mutate" and "mate" different solutions.  The set of solutions is called the population, and this setting defines the _size_ of that population.  The larger the population, the more likely you will get a best-fit solution faster, but it also means that the overall run time of the simulation will take longer.

Generally a population size between 128 and 1024 is sufficient for most Fanucci decks.

## Number of Iterations ##
Genetic algorithms "grow" or "evolve" their solutions through mating and mutating existing solutions.  The _number of times_ this "evolution" happens is determined by this setting.  The total number of iterations simply means the number of evolutions to perform until the algorithm is considered _complete_.

At the end of the run, the best solution is selected from the population and that is what is displayed to the user.

## Elitism Rate ##
When going through the evolution process, there are always some solutions that are better than others.  The _elitism rate_ defines what percentage of this population should be saved from one generation to the next.  The higher the percentage, the more "best solutions" get carried over.  Those solutions in the population that fall outside of this range are replaced with their offspring, which are determined by "mating" two random solutions with one another, and then possibly mutating their offspring as well (see the next section).

Be careful not to set the rate too high, or you will lose all benefit of the simulation.  An ideal elitism rate is usually 10-25%.

## Mutation Rate ##
Just like in nature, not all offspring come out perfect.  When we mate solutions with one another, there's a chance that the offsping solution could have something unique about it that isn't in either of its parents.  What does this mean for us?  Well, when two solutions mate in the YADFC(tm) simulator, we take the half the cards from two solutions and merge them together, creating a new set.  If that new offspring set should be mutated, then one of the cards in its set is removed and replaced with a completely new card chosen at random from the rest of the available cards in your deck.

The _mutation rate_ defines what percentage of offspring during the evolution should have this mutation happen.  Again it's best not to set this too high or you run the risk of not reaping any benefits of the original mating, i.e. you may never come up with an ideal solution.  Generally an ideal mutation rate is around 10-20%.

## Number of Sets ##
This one is simple.  This setting simply defines the number of Fanucci sets you want to generate, from 1 to 5.  Each set holds at most 4 cards and corresponds to the sets you would set in [LoZ](http://lgendsofzork.com/), namely the Fanucci Gambit, Mind, Body, Spirit and Sidekick slots.

## Local Maxima Count ##
This one is the most confusing of all the settings, since it's hard to explain it with just a few words.  Basically, the way a genetic algorithm works is that it starts breeding solutions with one another, creating a best-of-breed solution.  The algorithm then has two choices:

  1. Repeat the process for the specified number of iterations.
  1. Stop after you've seen the same "best fit" solution a pre-defined number of times.

It's this second option that we're interested in, and that's what this setting really means.  If we've seen the same solution repeated for the number of times specified by this setting, then exit the iteration loop early.  This can be a real time saver!

Here's an example.  Let's say that we have set our number of iterations at 10K, but we find our best fit solution after 20 iterations.  If we set this value to 30, then the system will stop iterating over the solutions after iteration 50 (since we found the best solution at iteration 20, and we said to stop iterating after we've seen it consecutively for 30 iterations, so 20+30=50).

It is important to note that this setting only makes sense if the elitism rate is set to something greater than zero.  Otherwise the system will never keep the best solutions and it is highly unlikely that the same best solution will be seen consecutively to break out of the iteration loop early.

If you do not want to break out of the loop at all and you want to run through all the iterations, then set this value to **0** (zero).

## Slot Solution Order ##
The default order that the best hands will be filled in for the slots is:
  1. Fanucci Gambit
  1. Mind
  1. Body
  1. Spirit
  1. Sidekick (if 5 slots are chosen to be filled in)

This is all well and good if you don't have a particular strategy in mind, i.e. work at becoming an Enchanter, Warrior or Scholar.  However, some people want to place their cards in a particular order to better meet their playing strategy, so the default order may not be to their liking.  That's fine, that's what this option is for.  :-)

If you want to change the order, simply select the item you wish to move and use the "Up" and "Down" buttons to the right of the list to move the items appropriately.  This provides a simple method to change the order the various slots will be filled in with the best hands.  The list will populate in the order specified, with the slot name at the top of the list being the first slot that will be filled in by the calculator.