package ch.admin.seco.chatserver.service;

import ch.admin.seco.chatserver.data.users.UserEntity;
import ch.admin.seco.chatserver.data.users.UserRepository;
import ch.admin.seco.chatserver.dto.users.status.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class UserServiceTest
{
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void whenClassifyByShouldState_givenUserListIsEmpty_thenReturnEmptyMap()
    {
        // given

        // when
        final Map<Status, Collection<UserEntity>> classifiedUsers = userService.classifyByShouldState(Collections.emptyList());

        // then
        assertThat(classifiedUsers)
                .hasSize(3);
        assertThat(classifiedUsers.get(Status.ONLINE))
                .isEmpty();
        assertThat(classifiedUsers.get(Status.AWAY))
                .isEmpty();
        assertThat(classifiedUsers.get(Status.OFFLINE))
                .isEmpty();
    }

    @Test
    public void whenTest()
    {
        // given
        final Instant now = Instant.now();
        final List<UserEntity> users = Arrays.asList(
                createUpdatedUser("1", 40),
                createUpdatedUser("2", 0),
                createUpdatedUser("3", 10),
                createUpdatedUser("4", 15),
                createUpdatedUser("5", 65),
                createUpdatedUser("6", 70),
                createUpdatedUser("7", 90),
                createUpdatedUser("8", 20),
                createUpdatedUser("9", 80),
                createUpdatedUser("10", 5));

        // when
        final Map<Status, Collection<UserEntity>> classifiedUsers = userService.classifyByShouldState(users);

        // then
        assertThat(classifiedUsers)
                .hasSize(3)
                .containsOnlyKeys(Status.ONLINE, Status.AWAY, Status.OFFLINE);
        assertThat(classifiedUsers.get(Status.ONLINE))
                .hasSize(5)
                .extracting(UserEntity::getNickname)
                .containsExactlyInAnyOrder("2", "3", "4", "8", "10");
        assertThat(classifiedUsers.get(Status.AWAY))
                .hasSize(1)
                .extracting(UserEntity::getNickname)
                .containsExactlyInAnyOrder("1");
        assertThat(classifiedUsers.get(Status.OFFLINE))
                .hasSize(4)
                .extracting(UserEntity::getNickname)
                .containsExactlyInAnyOrder("5", "6", "7", "9");
    }

    private UserEntity createUpdatedUser(final String user, final int minutes)
    {
        return new UserEntity(user, Status.ONLINE, 0, Instant.now().minus(minutes, ChronoUnit.MINUTES));
    }
}
