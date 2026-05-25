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
public final class UpdateGoalUseCase_Factory implements Factory<UpdateGoalUseCase> {
  private final Provider<GoalRepository> repositoryProvider;

  public UpdateGoalUseCase_Factory(Provider<GoalRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public UpdateGoalUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static UpdateGoalUseCase_Factory create(Provider<GoalRepository> repositoryProvider) {
    return new UpdateGoalUseCase_Factory(repositoryProvider);
  }

  public static UpdateGoalUseCase newInstance(GoalRepository repository) {
    return new UpdateGoalUseCase(repository);
  }
}
