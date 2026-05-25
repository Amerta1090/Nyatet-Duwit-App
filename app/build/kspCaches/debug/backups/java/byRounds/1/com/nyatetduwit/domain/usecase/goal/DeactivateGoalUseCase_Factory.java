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
public final class DeactivateGoalUseCase_Factory implements Factory<DeactivateGoalUseCase> {
  private final Provider<GoalRepository> repositoryProvider;

  public DeactivateGoalUseCase_Factory(Provider<GoalRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public DeactivateGoalUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static DeactivateGoalUseCase_Factory create(Provider<GoalRepository> repositoryProvider) {
    return new DeactivateGoalUseCase_Factory(repositoryProvider);
  }

  public static DeactivateGoalUseCase newInstance(GoalRepository repository) {
    return new DeactivateGoalUseCase(repository);
  }
}
