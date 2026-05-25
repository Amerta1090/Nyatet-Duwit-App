package com.nyatetduwit.domain.usecase.goal;

import com.nyatetduwit.domain.repository.GoalRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class AddGoalUseCase_Factory implements Factory<AddGoalUseCase> {
  private final Provider<GoalRepository> repositoryProvider;

  public AddGoalUseCase_Factory(Provider<GoalRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public AddGoalUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static AddGoalUseCase_Factory create(Provider<GoalRepository> repositoryProvider) {
    return new AddGoalUseCase_Factory(repositoryProvider);
  }

  public static AddGoalUseCase newInstance(GoalRepository repository) {
    return new AddGoalUseCase(repository);
  }
}
