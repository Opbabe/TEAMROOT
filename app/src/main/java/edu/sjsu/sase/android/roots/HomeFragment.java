package edu.sjsu.sase.android.roots;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    // Original UI components
    private RecyclerView featuredEventsRecyclerView;
    private RecyclerView upcomingEventsRecyclerView;
    private ExtendedFloatingActionButton fabCreateEvent;
    private TextInputEditText etSearch;
    private ChipGroup categoryChipGroup;

    // New UI components
    private RecyclerView nearbyEventsRecyclerView;
    private RecyclerView trendingEventsRecyclerView;
    private RecyclerView friendsGoingRecyclerView;
    private RecyclerView eventGroupsRecyclerView;
    private RecyclerView recommendedEventsRecyclerView;
    private BottomNavigationView bottomNavigationView;
    private ImageButton btnNotifications;
    private ImageView ivProfilePic;
    private TextInputLayout searchInputLayout;
    private LinearLayout searchFiltersLayout;
    private ChipGroup filterChipGroup;
    private MaterialButton btnClearFilters;
    private MaterialButton btnApplyFilters;
    private LinearLayout quickActionButtons;
    private FloatingActionButton fabJoinEvent;
    private FloatingActionButton fabFindFriends;
    private Toolbar toolbar;
    
    // See All TextViews
    private TextView tvSeeAllFeatured;
    private TextView tvSeeAllUpcoming;
    private TextView tvSeeAllNearby;
    private TextView tvSeeAllTrending;
    private TextView tvSeeAllFriends;
    private TextView tvSeeAllGroups;
    private TextView tvSeeAllRecommended;
    
    // Quick Access Category Views
    private LinearLayout quickAccessMusic;
    private LinearLayout quickAccessArt;
    private LinearLayout quickAccessFood;
    private LinearLayout quickAccessSports;
    private LinearLayout quickAccessTech;

    // Data lists
    private List<Event> featuredEvents = new ArrayList<>();
    private List<Event> upcomingEvents = new ArrayList<>();
    private List<Event> nearbyEvents = new ArrayList<>();
    private List<Event> trendingEvents = new ArrayList<>();
    private List<Event> recommendedEvents = new ArrayList<>();
    private List<Friend> friendsGoing = new ArrayList<>();
    private List<EventGroup> eventGroups = new ArrayList<>();
    private List<String> categories = new ArrayList<>();

    public HomeFragment() {
        // Required empty constructor.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize original UI components
        featuredEventsRecyclerView = view.findViewById(R.id.featuredEventsRecyclerView);
        upcomingEventsRecyclerView = view.findViewById(R.id.upcomingEventsRecyclerView);
        fabCreateEvent = view.findViewById(R.id.fabCreateEvent);
        etSearch = view.findViewById(R.id.etSearch);
        categoryChipGroup = view.findViewById(R.id.categoryChipGroup);

        // Initialize new UI components
        nearbyEventsRecyclerView = view.findViewById(R.id.nearbyEventsRecyclerView);
        trendingEventsRecyclerView = view.findViewById(R.id.trendingEventsRecyclerView);
        friendsGoingRecyclerView = view.findViewById(R.id.friendsGoingRecyclerView);
        eventGroupsRecyclerView = view.findViewById(R.id.eventGroupsRecyclerView);
        recommendedEventsRecyclerView = view.findViewById(R.id.recommendedEventsRecyclerView);
        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        btnNotifications = view.findViewById(R.id.btnNotifications);
        ivProfilePic = view.findViewById(R.id.ivProfilePic);
        searchInputLayout = view.findViewById(R.id.searchInputLayout);
        searchFiltersLayout = view.findViewById(R.id.searchFiltersLayout);
        filterChipGroup = view.findViewById(R.id.filterChipGroup);
        btnClearFilters = view.findViewById(R.id.btnClearFilters);
        btnApplyFilters = view.findViewById(R.id.btnApplyFilters);
        quickActionButtons = view.findViewById(R.id.quickActionButtons);
        fabJoinEvent = view.findViewById(R.id.fabJoinEvent);
        fabFindFriends = view.findViewById(R.id.fabFindFriends);
        toolbar = view.findViewById(R.id.toolbar);
        
        // Initialize See All TextViews
        tvSeeAllFeatured = view.findViewById(R.id.tvSeeAllFeatured);
        tvSeeAllUpcoming = view.findViewById(R.id.tvSeeAllUpcoming);
        tvSeeAllNearby = view.findViewById(R.id.tvSeeAllNearby);
        tvSeeAllTrending = view.findViewById(R.id.tvSeeAllTrending);
        tvSeeAllFriends = view.findViewById(R.id.tvSeeAllFriends);
        tvSeeAllGroups = view.findViewById(R.id.tvSeeAllGroups);
        tvSeeAllRecommended = view.findViewById(R.id.tvSeeAllRecommended);
        
        // Initialize Quick Access Category Views
        quickAccessMusic = view.findViewById(R.id.quickAccessMusic);
        quickAccessArt = view.findViewById(R.id.quickAccessArt);
        quickAccessFood = view.findViewById(R.id.quickAccessFood);
        quickAccessSports = view.findViewById(R.id.quickAccessSports);
        quickAccessTech = view.findViewById(R.id.quickAccessTech);

        // Setup all components
        setupToolbar();
        setupRecyclerViews();
        setupBottomNavigation();
        setupSearchBar();
        setupQuickAccessCategories();
        setupSeeAllButtons();
        setupFab();
        setupQuickActionButtons();
        setupCategories();
        
        // Populate with dummy data
        populateDummyData();
    }

    private void setupToolbar() {
        // Setup notification button
        btnNotifications.setOnClickListener(v -> 
            Toast.makeText(getContext(), "Notifications clicked", Toast.LENGTH_SHORT).show()
        );
        
        // Setup profile picture
        ivProfilePic.setOnClickListener(v -> 
            Toast.makeText(getContext(), "Profile clicked", Toast.LENGTH_SHORT).show()
        );
    }

    private void setupRecyclerViews() {
        // Original RecyclerViews
        featuredEventsRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        upcomingEventsRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        // New RecyclerViews
        nearbyEventsRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        trendingEventsRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        friendsGoingRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        eventGroupsRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recommendedEventsRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        // Set adapters for original RecyclerViews
        featuredEventsRecyclerView.setAdapter(new EventAdapter(featuredEvents, R.layout.item_event_featured));
        upcomingEventsRecyclerView.setAdapter(new EventAdapter(upcomingEvents, R.layout.item_event));
        
        // Set adapters for new RecyclerViews
        nearbyEventsRecyclerView.setAdapter(new EventAdapter(nearbyEvents, R.layout.item_event_nearby));
        trendingEventsRecyclerView.setAdapter(new EventAdapter(trendingEvents, R.layout.item_event_trending));
        friendsGoingRecyclerView.setAdapter(new FriendAdapter(friendsGoing, R.layout.item_friend_going));
        eventGroupsRecyclerView.setAdapter(new EventGroupAdapter(eventGroups, R.layout.item_event_group));
        recommendedEventsRecyclerView.setAdapter(new EventAdapter(recommendedEvents, R.layout.item_event));
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_profile) {
                Toast.makeText(getContext(), "Profile clicked", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.navigation_home) {
                Toast.makeText(getContext(), "Home clicked", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.navigation_buddies) {
                Toast.makeText(getContext(), "Buddies clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });
    }

    private void setupSearchBar() {
        // Setup search end icon click to toggle filters
        searchInputLayout.setEndIconOnClickListener(v -> {
            if (searchFiltersLayout.getVisibility() == View.VISIBLE) {
                searchFiltersLayout.setVisibility(View.GONE);
            } else {
                searchFiltersLayout.setVisibility(View.VISIBLE);
            }
        });
        
        // Setup filter buttons
        btnClearFilters.setOnClickListener(v -> {
            // Clear all selected filters
            for (int i = 0; i < filterChipGroup.getChildCount(); i++) {
                Chip chip = (Chip) filterChipGroup.getChildAt(i);
                chip.setChecked(false);
            }
            Toast.makeText(getContext(), "Filters cleared", Toast.LENGTH_SHORT).show();
        });
        
        btnApplyFilters.setOnClickListener(v -> {
            // Apply selected filters
            StringBuilder selectedFilters = new StringBuilder("Applied filters: ");
            boolean hasSelectedFilters = false;
            
            for (int i = 0; i < filterChipGroup.getChildCount(); i++) {
                Chip chip = (Chip) filterChipGroup.getChildAt(i);
                if (chip.isChecked()) {
                    if (hasSelectedFilters) {
                        selectedFilters.append(", ");
                    }
                    selectedFilters.append(chip.getText());
                    hasSelectedFilters = true;
                }
            }
            
            if (!hasSelectedFilters) {
                selectedFilters.append("None");
            }
            
            Toast.makeText(getContext(), selectedFilters.toString(), Toast.LENGTH_SHORT).show();
            searchFiltersLayout.setVisibility(View.GONE);
        });
        
        // Setup search action
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            Toast.makeText(getContext(), "Searching for: " + etSearch.getText(), Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void setupQuickAccessCategories() {
        quickAccessMusic.setOnClickListener(v -> 
            Toast.makeText(getContext(), "Music category clicked", Toast.LENGTH_SHORT).show()
        );
        
        quickAccessArt.setOnClickListener(v -> 
            Toast.makeText(getContext(), "Art category clicked", Toast.LENGTH_SHORT).show()
        );
        
        quickAccessFood.setOnClickListener(v -> 
            Toast.makeText(getContext(), "Food category clicked", Toast.LENGTH_SHORT).show()
        );
        
        quickAccessSports.setOnClickListener(v -> 
            Toast.makeText(getContext(), "Sports category clicked", Toast.LENGTH_SHORT).show()
        );
        
        quickAccessTech.setOnClickListener(v -> 
            Toast.makeText(getContext(), "Tech category clicked", Toast.LENGTH_SHORT).show()
        );
    }

    private void setupSeeAllButtons() {
        tvSeeAllFeatured.setOnClickListener(v -> 
            Toast.makeText(getContext(), "See all featured events", Toast.LENGTH_SHORT).show()
        );
        
        tvSeeAllUpcoming.setOnClickListener(v -> 
            Toast.makeText(getContext(), "See all upcoming events", Toast.LENGTH_SHORT).show()
        );
        
        tvSeeAllNearby.setOnClickListener(v -> 
            Toast.makeText(getContext(), "See all nearby events", Toast.LENGTH_SHORT).show()
        );
        
        tvSeeAllTrending.setOnClickListener(v -> 
            Toast.makeText(getContext(), "See all trending events", Toast.LENGTH_SHORT).show()
        );
        
        tvSeeAllFriends.setOnClickListener(v -> 
            Toast.makeText(getContext(), "See all friends going", Toast.LENGTH_SHORT).show()
        );
        
        tvSeeAllGroups.setOnClickListener(v -> 
            Toast.makeText(getContext(), "See all event groups", Toast.LENGTH_SHORT).show()
        );
        
        tvSeeAllRecommended.setOnClickListener(v -> 
            Toast.makeText(getContext(), "See all recommended events", Toast.LENGTH_SHORT).show()
        );
    }

    private void setupFab() {
        fabCreateEvent.setOnClickListener(v ->
            Toast.makeText(getContext(), "Create Event button clicked", Toast.LENGTH_SHORT).show()
        );
    }

    private void setupQuickActionButtons() {
        // Show/hide quick action buttons on scroll
        // For demo purposes, make them visible
        quickActionButtons.setVisibility(View.VISIBLE);
        
        fabJoinEvent.setOnClickListener(v -> 
            Toast.makeText(getContext(), "Join Event clicked", Toast.LENGTH_SHORT).show()
        );
        
        fabFindFriends.setOnClickListener(v -> 
            Toast.makeText(getContext(), "Find Friends clicked", Toast.LENGTH_SHORT).show()
        );
    }

    private void populateDummyData() {
        // Clear existing data
        featuredEvents.clear();
        upcomingEvents.clear();
        nearbyEvents.clear();
        trendingEvents.clear();
        recommendedEvents.clear();
        friendsGoing.clear();
        eventGroups.clear();
        
        // Add featured events
        featuredEvents.add(new Event("Summer Music Festival", "July 4, 2023", "Central Park, NYC"));
        featuredEvents.add(new Event("Art Gala", "August 12, 2023", "Modern Art Museum"));
        featuredEvents.add(new Event("Food Fiesta", "September 5, 2023", "Downtown"));

        // Add upcoming events
        upcomingEvents.add(new Event("Tech Conference", "October 20, 2023", "Moscone Center"));
        upcomingEvents.add(new Event("Charity Run", "November 10, 2023", "Golden Gate Park"));
        upcomingEvents.add(new Event("Winter Wonderland", "December 15, 2023", "City Arena"));
        
        // Add nearby events
        nearbyEvents.add(new Event("Local Jazz Night", "Tomorrow, 8 PM", "Blue Note Club", "2.5 miles away"));
        nearbyEvents.add(new Event("Farmers Market", "Saturday, 9 AM", "Union Square", "1.2 miles away"));
        nearbyEvents.add(new Event("Poetry Reading", "Sunday, 4 PM", "City Library", "0.8 miles away"));
        
        // Add trending events
        trendingEvents.add(new Event("EDM Festival", "August 25, 2023", "Randall's Island", "Music"));
        trendingEvents.add(new Event("Comic Con", "September 15, 2023", "Javits Center", "Entertainment"));
        trendingEvents.add(new Event("Food & Wine Festival", "October 5, 2023", "Hudson Yards", "Food"));
        
        // Add recommended events
        recommendedEvents.add(new Event("Photography Workshop", "November 5, 2023", "Camera Club"));
        recommendedEvents.add(new Event("Indie Film Screening", "November 12, 2023", "Angelika Theater"));
        recommendedEvents.add(new Event("Cooking Class", "November 20, 2023", "Culinary Institute"));
        
        // Add friends going
        friendsGoing.add(new Friend("Alex Kim", "Summer Music Festival"));
        friendsGoing.add(new Friend("Jamie Smith", "Art Gala"));
        friendsGoing.add(new Friend("Taylor Wong", "Food Fiesta"));
        friendsGoing.add(new Friend("Jordan Lee", "Tech Conference"));
        
        // Add event groups
        eventGroups.add(new EventGroup("Festival Squad", "Summer Music Festival", 45));
        eventGroups.add(new EventGroup("Art Enthusiasts", "Art Gala", 28));
        eventGroups.add(new EventGroup("Foodies Unite", "Food Fiesta", 67));
        eventGroups.add(new EventGroup("Tech Nerds", "Tech Conference", 32));
        
        // Notify adapters of data changes
        if (featuredEventsRecyclerView.getAdapter() != null) {
            featuredEventsRecyclerView.getAdapter().notifyDataSetChanged();
        }
        if (upcomingEventsRecyclerView.getAdapter() != null) {
            upcomingEventsRecyclerView.getAdapter().notifyDataSetChanged();
        }
        if (nearbyEventsRecyclerView.getAdapter() != null) {
            nearbyEventsRecyclerView.getAdapter().notifyDataSetChanged();
        }
        if (trendingEventsRecyclerView.getAdapter() != null) {
            trendingEventsRecyclerView.getAdapter().notifyDataSetChanged();
        }
        if (recommendedEventsRecyclerView.getAdapter() != null) {
            recommendedEventsRecyclerView.getAdapter().notifyDataSetChanged();
        }
        if (friendsGoingRecyclerView.getAdapter() != null) {
            friendsGoingRecyclerView.getAdapter().notifyDataSetChanged();
        }
        if (eventGroupsRecyclerView.getAdapter() != null) {
            eventGroupsRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    private void setupCategories() {
        // Clear existing categories and chips
        categories.clear();
        categoryChipGroup.removeAllViews();
        
        // Add categories
        categories.add("Music");
        categories.add("Art");
        categories.add("Food");
        categories.add("Tech");
        categories.add("Sports");
        categories.add("Education");
        categories.add("Networking");
        categories.add("Charity");
        categories.add("Family");
        categories.add("Outdoors");
        
        for (String category : categories) {
            Chip chip = new Chip(requireContext());
            chip.setText(category);
            chip.setCheckable(true);
            chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                // Handle category selection
                Toast.makeText(getContext(), category + " " + (isChecked ? "selected" : "unselected"), Toast.LENGTH_SHORT).show();
            });
            categoryChipGroup.addView(chip);
        }
    }
    
    // Friend Adapter
    public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {
        private List<Friend> friends;
        private int layoutResId;
        
        public FriendAdapter(List<Friend> friends, int layoutResId) {
            this.friends = friends;
            this.layoutResId = layoutResId;
        }
        
        @NonNull
        @Override
        public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
            return new FriendViewHolder(view);
        }
        
        @Override
        public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
            Friend friend = friends.get(position);
            holder.tvFriendName.setText(friend.getName());
            holder.tvEventName.setText("Going to: " + friend.getEventAttending());
            
            // Set click listener for chat button
            holder.btnChat.setOnClickListener(v -> 
                Toast.makeText(getContext(), "Chat with " + friend.getName(), Toast.LENGTH_SHORT).show()
            );
        }
        
        @Override
        public int getItemCount() {
            return friends.size();
        }
        
        class FriendViewHolder extends RecyclerView.ViewHolder {
            ImageView ivFriendProfile;
            TextView tvFriendName;
            TextView tvEventName;
            MaterialButton btnChat;
            
            FriendViewHolder(View itemView) {
                super(itemView);
                ivFriendProfile = itemView.findViewById(R.id.ivFriendProfile);
                tvFriendName = itemView.findViewById(R.id.tvFriendName);
                tvEventName = itemView.findViewById(R.id.tvEventName);
                btnChat = itemView.findViewById(R.id.btnChat);
            }
        }
    }
    
    // Event Group Adapter
    public class EventGroupAdapter extends RecyclerView.Adapter<EventGroupAdapter.EventGroupViewHolder> {
        private List<EventGroup> eventGroups;
        private int layoutResId;
        
        public EventGroupAdapter(List<EventGroup> eventGroups, int layoutResId) {
            this.eventGroups = eventGroups;
            this.layoutResId = layoutResId;
        }
        
        @NonNull
        @Override
        public EventGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
            return new EventGroupViewHolder(view);
        }
        
        @Override
        public void onBindViewHolder(@NonNull EventGroupViewHolder holder, int position) {
            EventGroup group = eventGroups.get(position);
            holder.tvGroupName.setText(group.getName());
            holder.tvEventName.setText("For: " + group.getEventName());
            holder.tvMemberCount.setText("+" + group.getMemberCount() + " members");
            
            // Set click listener for join button
            holder.btnJoinGroup.setOnClickListener(v -> 
                Toast.makeText(getContext(), "Joined " + group.getName(), Toast.LENGTH_SHORT).show()
            );
        }
        
        @Override
        public int getItemCount() {
            return eventGroups.size();
        }
        
        class EventGroupViewHolder extends RecyclerView.ViewHolder {
            ImageView ivGroupImage;
            TextView tvGroupName;
            TextView tvEventName;
            TextView tvMemberCount;
            MaterialButton btnJoinGroup;
            
            EventGroupViewHolder(View itemView) {
                super(itemView);
                ivGroupImage = itemView.findViewById(R.id.ivGroupImage);
                tvGroupName = itemView.findViewById(R.id.tvGroupName);
                tvEventName = itemView.findViewById(R.id.tvEventName);
                tvMemberCount = itemView.findViewById(R.id.tvMemberCount);
                btnJoinGroup = itemView.findViewById(R.id.btnJoinGroup);
            }
        }
    }
}

