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
public final class GetGoalByIdUseCase_Factory implements Factory<GetGoalByIdUseCase> {
  private final Provider<GoalRepository> repositoryProvider;

  public GetGoalByIdUseCase_Factory(Provider<GoalRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetGoalByIdUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetGoalByIdUseCase_Factory create(Provider<GoalRepository> repositoryProvider) {
    return new GetGoalByIdUseCase_Factory(repositoryProvider);
  }

  public static GetGoalByIdUseCase newInstance(GoalRepository repository) {
    return new GetGoalByIdUseCase(repository);
  }
}
