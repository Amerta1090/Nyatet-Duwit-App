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
public final class DeleteGoalUseCase_Factory implements Factory<DeleteGoalUseCase> {
  private final Provider<GoalRepository> repositoryProvider;

  public DeleteGoalUseCase_Factory(Provider<GoalRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public DeleteGoalUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static DeleteGoalUseCase_Factory create(Provider<GoalRepository> repositoryProvider) {
    return new DeleteGoalUseCase_Factory(repositoryProvider);
  }

  public static DeleteGoalUseCase newInstance(GoalRepository repository) {
    return new DeleteGoalUseCase(repository);
  }
}
